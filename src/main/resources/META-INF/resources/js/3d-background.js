// Enhanced Three.js setup for Sci-Fi Cyberwave theme
let camera, scene, renderer;
let isLowPerformanceDevice = false;
let lastFrameTime = 0;
let frameCount = 0;
let lastGcTime = 0;
let animationFrameId;
let geometry, material, mesh;
let mouseX = 0, mouseY = 0;
let windowHalfX = window.innerWidth / 2;
let windowHalfY = window.innerHeight / 2;
let terminalGroup, gridMesh;
let clock = new THREE.Clock();
let terminalSkills = ["Kotlin", "Quarkus", "React", "WebGL", "Cloud Native", "REST APIs"];
let currentSkill = 0;
let typedText = "";
let isTyping = true;
let typingSpeed = 100; // ms per character
let lastTypingTime = 0;
let themeIsDark = true;

// Ultra vibrant color palette to match CSS
const COLORS = {
  // Dark theme colors
  dark: {
    base: 0x000000,           // Black base
    cyberCyan: 0x00ffff,      // Electric cyan
    neonPink: 0xff00ff,       // Hot pink
    neonPurple: 0x9900ff,     // Bright purple
    neonGreen: 0x00ff00,      // Electric green
    neonOrange: 0xff6600,     // Bright orange
    neonBlue: 0x0066ff,       // Electric blue
    neonYellow: 0xffff00      // Electric yellow
  },
  // Light theme colors
  light: {
    base: 0xffffff,           // White base
    cyberCyan: 0x00bbff,      // Blue for light mode
    neonPink: 0xff0066,       // Pink for light mode 
    neonPurple: 0x8800cc,     // Purple for light mode
    neonGreen: 0x00cc00,      // Green for light mode
    neonOrange: 0xff5500,     // Orange for light mode
    neonBlue: 0x0055ff,       // Blue for light mode
    neonYellow: 0xffcc00      // Yellow for light mode
  }
};

// Check device performance
function checkPerformance() {
  const startTime = performance.now();
  let testArr = [];
  
  // Create objects to test memory and CPU
  for (let i = 0; i < 10000; i++) {
    testArr.push({ test: i });
  }
  
  const endTime = performance.now();
  const duration = endTime - startTime;
  
  // Clean up test objects
  testArr = null;
  
  // If performance test takes more than 50ms, consider it a low-performance device
  return duration > 50;
}

function init() {
  // Detect performance
  isLowPerformanceDevice = checkPerformance();
  console.log('Low performance device?', isLowPerformanceDevice);
  
  if (isLowPerformanceDevice) {
    // Apply even more aggressive optimizations for low-performance devices
    document.body.classList.add('low-performance');
  }
  console.log('3D background init called');
  
  // Canvas setup
  const canvas = document.getElementById('bg-canvas');
  console.log('bg-canvas element:', canvas);
  
  // Scene setup
  scene = new THREE.Scene();
  updateSceneColors();
  
  // Camera setup with wider FOV for more dramatic effect
  camera = new THREE.PerspectiveCamera(80, window.innerWidth / window.innerHeight, 0.1, 1000);
  camera.position.z = 30;
  
  // Add OrbitControls with restricted polar angle - with error handling
  let controls;
  try {
    if (typeof THREE.OrbitControls === 'function') {
      controls = new THREE.OrbitControls(camera, canvas);
      controls.enableZoom = false;
      controls.enablePan = false;
      controls.maxPolarAngle = Math.PI / 3;
      controls.minPolarAngle = Math.PI / 6;
      controls.update();
      console.log('OrbitControls initialized successfully');
    } else {
      console.warn('THREE.OrbitControls is not available');
    }
  } catch (error) {
    console.error('Error initializing OrbitControls:', error);
  }
  
  // Create dynamic grid
  createDynamicGrid();
  
  // Create floating 3D terminal
  createFloatingTerminal();
  console.log('Created floating terminal:', terminalGroup);
  
  // Add star particles
  addStars();
  
  // Add glowing light beams
  addLightBeams();
  
  // Renderer setup
  renderer = new THREE.WebGLRenderer({
    canvas: canvas,
    antialias: true,
    alpha: true
  });
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
  
  // Mouse move event
  document.addEventListener('mousemove', onDocumentMouseMove);
  
  // Device orientation for mobile
  if (window.DeviceOrientationEvent) {
    window.addEventListener('deviceorientation', onDeviceOrientation);
  }
  
  // Handle window resize
  window.addEventListener('resize', onWindowResize);
  
  // Handle theme changes
  document.getElementById('theme-toggle').addEventListener('click', function() {
    themeIsDark = !document.body.hasAttribute('data-theme') || 
                 document.body.getAttribute('data-theme') !== 'light';
    updateSceneColors();
  });
  
  // Initialize project card 3D models
  initProjectModels();
}

function updateSceneColors() {
  // Set colors based on current theme
  const colors = themeIsDark ? COLORS.dark : COLORS.light;
  scene.background = new THREE.Color(colors.base);
  scene.fog = new THREE.FogExp2(colors.base, 0.02);
  
  // Update existing objects
  scene.traverse(function(object) {
    if (object.material && object.userData.type === 'grid') {
      object.material.color.set(colors.cyberCyan);
    }
    if (object.material && object.userData.type === 'terminal') {
      object.material.color.set(colors.base);
      object.material.emissive.set(colors.cyberCyan);
    }
    if (object.material && object.userData.type === 'text') {
      object.material.color.set(colors.cyberCyan);
    }
    if (object.material && object.userData.type === 'lightBeam') {
      const randomColor = [colors.cyberCyan, colors.neonPink, colors.neonPurple, 
                          colors.neonGreen, colors.neonOrange][Math.floor(Math.random() * 5)];
      object.material.color.set(randomColor);
    }
  });
}

function createDynamicGrid() {
  // Create more detailed grid with cyberpunk styling
  const gridSize = 200;
  const gridDivisions = 20; // Reduced from 40 for better performance
  
  // Main grid
  const gridGeometry = new THREE.PlaneGeometry(gridSize, gridSize, gridDivisions, gridDivisions);
  const gridMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    wireframe: true,
    transparent: true,
    opacity: 0.3
  });
  
  gridMesh = new THREE.Mesh(gridGeometry, gridMaterial);
  gridMesh.rotation.x = -Math.PI / 2;
  gridMesh.position.y = -30;
  gridMesh.userData.type = 'grid';
  scene.add(gridMesh);
  
  // Secondary grid for added depth
  const gridGeometry2 = new THREE.PlaneGeometry(gridSize * 2, gridSize * 2, gridDivisions / 2, gridDivisions / 2);
  const gridMaterial2 = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.neonPink : COLORS.light.neonPink,
    wireframe: true,
    transparent: true,
    opacity: 0.1
  });
  
  const gridMesh2 = new THREE.Mesh(gridGeometry2, gridMaterial2);
  gridMesh2.rotation.x = -Math.PI / 2;
  gridMesh2.position.y = -35;
  gridMesh2.userData.type = 'grid';
  scene.add(gridMesh2);
  
  // Add ambient and directional light
  const ambientLight = new THREE.AmbientLight(0x404040, 0.5);
  scene.add(ambientLight);
  
  const directionalLight = new THREE.DirectionalLight(
    themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan, 
    1
  );
  directionalLight.position.set(0, 50, 30);
  scene.add(directionalLight);
}

function createFloatingTerminal() {
  // Create a more detailed terminal
  terminalGroup = new THREE.Group();
  scene.add(terminalGroup);
  
  // Terminal base - larger and more prominent
  const baseGeometry = new THREE.BoxGeometry(30, 15, 1);
  const baseMaterial = new THREE.MeshPhongMaterial({
    color: themeIsDark ? COLORS.dark.base : COLORS.light.base,
    transparent: true,
    opacity: 0.8,
    shininess: 100,
    specular: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    emissive: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    emissiveIntensity: 0.2
  });
  
  const base = new THREE.Mesh(baseGeometry, baseMaterial);
  base.userData.type = 'terminal';
  terminalGroup.add(base);
  
  // Terminal frame with more pronounced glow
  const frameGeometry = new THREE.BoxGeometry(30.5, 15.5, 1.1);
  const frameMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    transparent: true,
    opacity: 0.5
  });
  
  const frame = new THREE.Mesh(frameGeometry, frameMaterial);
  frame.userData.type = 'terminal';
  terminalGroup.add(frame);
  
  // Terminal header bar
  const headerGeometry = new THREE.BoxGeometry(30, 2, 1.1);
  const headerMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.neonPink : COLORS.light.neonPink,
    transparent: true,
    opacity: 0.7
  });
  
  const header = new THREE.Mesh(headerGeometry, headerMaterial);
  header.position.y = 6.5;
  header.userData.type = 'terminal';
  terminalGroup.add(header);
  
  // Add decorative elements to the terminal
  // Data lines
  for (let i = 0; i < 5; i++) {
    const lineGeometry = new THREE.BoxGeometry(25, 0.2, 0.1);
    const lineMaterial = new THREE.MeshBasicMaterial({
      color: themeIsDark ? 
        [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple][i % 3] : 
        [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple][i % 3],
      transparent: true,
      opacity: 0.7
    });
    
    const line = new THREE.Mesh(lineGeometry, lineMaterial);
    line.position.y = 3 - i * 1.5;
    line.userData.type = 'terminal';
    terminalGroup.add(line);
  }
  
  // Position the terminal
  terminalGroup.position.set(0, 8, 0);
  
  // Load font for TextGeometry
  const fontLoader = new THREE.FontLoader();
  fontLoader.load('https://threejs.org/examples/fonts/helvetiker_regular.typeface.json', function(font) {
    console.log('Font loaded, creating terminal elements');
    
    // Create terminal title - simplified to box
    const titleWidth = 15;
    const titleGeometry = new THREE.BoxGeometry(titleWidth, 1, 0.1);
    const titleMaterial = new THREE.MeshBasicMaterial({
      color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan
    });
    
    const titleMesh = new THREE.Mesh(titleGeometry, titleMaterial);
    titleMesh.position.set(0, 6.5, 0.6);
    titleMesh.userData.type = 'text';
    titleMesh.userData.text = 'SYSTEM TERMINAL';
    terminalGroup.add(titleMesh);
    
    // Create prompt
    const promptGeometry = new THREE.BoxGeometry(1, 1, 0.1);
    const promptMaterial = new THREE.MeshBasicMaterial({
      color: themeIsDark ? COLORS.dark.neonGreen : COLORS.light.neonGreen
    });
    
    const promptMesh = new THREE.Mesh(promptGeometry, promptMaterial);
    promptMesh.position.set(-13, 3, 0.6);
    promptMesh.userData.type = 'text';
    terminalGroup.add(promptMesh);
    
    // Create initial text
    createTerminalText(font, "");
  });
}

function createTerminalText(font, text) {
  // Remove existing text
  terminalGroup.children.forEach(child => {
    if (child.userData.isText) {
      terminalGroup.remove(child);
    }
  });
  
  console.log('Creating terminal text:', text);
  
  // Simple text box instead of TextGeometry
  const textWidth = text.length * 0.8;
  const textGeometry = new THREE.BoxGeometry(textWidth, 1, 0.1);
  
  const textMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan
  });
  
  const textMesh = new THREE.Mesh(textGeometry, textMaterial);
  textMesh.position.set(-13 + textWidth/2, 3, 0.6);
  textMesh.userData.isText = true;
  textMesh.userData.type = 'text';
  textMesh.userData.text = text; // Store the text
  
  terminalGroup.add(textMesh);
}

function addStars() {
  // Create more stars with different colors
  const starsGeometry = new THREE.BufferGeometry();
  const starsMaterial = new THREE.PointsMaterial({
    color: 0xffffff,
    size: 0.2,
    transparent: true,
    opacity: 0.8
  });
  
  const starsVertices = [];
  const starsColors = [];
  
  // Color array with vibrant colors
  const colorOptions = [
    new THREE.Color(0xffffff), // white
    new THREE.Color(COLORS.dark.cyberCyan),
    new THREE.Color(COLORS.dark.neonPink),
    new THREE.Color(COLORS.dark.neonPurple)
  ];
  
  for (let i = 0; i < 1000; i++) { // Reduced from 3000 for better performance
    const x = (Math.random() - 0.5) * 1000;
    const y = (Math.random() - 0.5) * 500;
    const z = (Math.random() - 0.5) * 1000;
    starsVertices.push(x, y, z);
    
    // Assign random color
    const color = colorOptions[Math.floor(Math.random() * colorOptions.length)];
    starsColors.push(color.r, color.g, color.b);
  }
  
  starsGeometry.setAttribute('position', new THREE.Float32BufferAttribute(starsVertices, 3));
  starsGeometry.setAttribute('color', new THREE.Float32BufferAttribute(starsColors, 3));
  
  starsMaterial.vertexColors = true;
  
  const stars = new THREE.Points(starsGeometry, starsMaterial);
  scene.add(stars);
}

function addLightBeams() {
  // Add cyberpunk light beams
  const beamCount = 4; // Reduced from 8 for better performance
  const colors = themeIsDark ? 
    [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple, COLORS.dark.neonGreen, COLORS.dark.neonOrange] : 
    [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple, COLORS.light.neonGreen, COLORS.light.neonOrange];
  
  for (let i = 0; i < beamCount; i++) {
    const angle = (i / beamCount) * Math.PI * 2;
    const distance = 70 + Math.random() * 30;
    
    const x = Math.cos(angle) * distance;
    const z = Math.sin(angle) * distance;
    
    const height = 100 + Math.random() * 50;
    const width = 1 + Math.random() * 2;
    
    const beamGeometry = new THREE.BoxGeometry(width, height, width);
    const beamMaterial = new THREE.MeshBasicMaterial({
      color: colors[Math.floor(Math.random() * colors.length)],
      transparent: true,
      opacity: 0.3 + Math.random() * 0.3
    });
    
    const beam = new THREE.Mesh(beamGeometry, beamMaterial);
    beam.position.set(x, height/2 - 30, z);
    beam.userData.type = 'lightBeam';
    beam.userData.rotationSpeed = 0.1 + Math.random() * 0.3;
    beam.userData.pulseSpeed = 0.5 + Math.random() * 0.5;
    scene.add(beam);
  }
}

function initProjectModels() {
  // Find all model containers
  console.log('Initializing project models');
  const modelContainers = document.querySelectorAll('.model-container');
  console.log('Found model containers:', modelContainers.length);
  
  if (modelContainers.length === 0) {
    console.log('No model containers found');
    return;
  }
  
  modelContainers.forEach((container, index) => {
    console.log('Processing container:', index);
    
    try {
      // Create a separate scene for each model
      const modelScene = new THREE.Scene();
      
      // Create a camera for this model
      const modelCamera = new THREE.PerspectiveCamera(50, container.clientWidth / container.clientHeight, 0.1, 1000);
      modelCamera.position.z = 5;
      
      // Create a renderer
      const modelRenderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
      modelRenderer.setSize(container.clientWidth, container.clientHeight);
      container.appendChild(modelRenderer.domElement);
      
      // Create dynamic geometries based on index
      let geometry;
      switch(index % 4) {
      case 0:
        // Sphere
        geometry = new THREE.SphereGeometry(1.5, 32, 32);
        break;
      case 1:
        // Torus
        geometry = new THREE.TorusGeometry(1.2, 0.5, 16, 32);
        break;
      case 2:
        // Icosahedron
        geometry = new THREE.IcosahedronGeometry(1.5, 1);
        break;
      case 3:
        // Octahedron
        geometry = new THREE.OctahedronGeometry(1.5, 1);
        break;
    }
    
    // Create material with the liquid metal look
    const mainColor = themeIsDark ? 
      [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple, COLORS.dark.neonGreen][index % 4] : 
      [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple, COLORS.light.neonGreen][index % 4];
    
    const material = new THREE.MeshStandardMaterial({
      color: mainColor,
      metalness: 0.9,  // High metalness for liquid metal look
      roughness: 0.2,  // Low roughness for shine
      transparent: true,
      opacity: 0.9
    });
    
    const mesh = new THREE.Mesh(geometry, material);
    modelScene.add(mesh);
    
    // Add wireframe overlay
    const wireframeGeometry = new THREE.WireframeGeometry(geometry);
    const wireframeMaterial = new THREE.LineBasicMaterial({
      color: 0xffffff,
      transparent: true,
      opacity: 0.3
    });
    
    const wireframe = new THREE.LineSegments(wireframeGeometry, wireframeMaterial);
    modelScene.add(wireframe);
    
    // Add ambient and point lights
    const ambientLight = new THREE.AmbientLight(0x404040, 0.5);
    modelScene.add(ambientLight);
    
    const pointLight = new THREE.PointLight(mainColor, 1, 10);
    pointLight.position.set(3, 3, 3);
    modelScene.add(pointLight);
    
    // Animation function with more complex motion
    function animateModel() {
      requestAnimationFrame(animateModel);
      
      const time = Date.now() * 0.001;
      
      // Rotating motion
      mesh.rotation.x = time * 0.3;
      mesh.rotation.y = time * 0.5;
      wireframe.rotation.x = time * 0.3;
      wireframe.rotation.y = time * 0.5;
      
      // Floating + pulsing motion
      mesh.position.y = Math.sin(time * 0.7) * 0.3;
      wireframe.position.y = Math.sin(time * 0.7) * 0.3;
      
      const scale = 1 + Math.sin(time * 1.5) * 0.1;
      mesh.scale.set(scale, scale, scale);
      wireframe.scale.set(scale, scale, scale);
      
      // Adjust light intensity
      pointLight.intensity = 1 + Math.sin(time * 2) * 0.5;
      
      modelRenderer.render(modelScene, modelCamera);
    }
    
    animateModel();
    } catch (error) {
      console.error('Error initializing 3D model:', error);
    }
  });
}

function onDocumentMouseMove(event) {
  mouseX = (event.clientX - windowHalfX) / 100;
  mouseY = (event.clientY - windowHalfY) / 100;
}

function onDeviceOrientation(event) {
  if (event.beta && event.gamma) {
    // Limit rotation to 7 degrees as specified
    const maxRotation = 7;
    const betaRotation = Math.min(Math.max(event.beta - 90, -maxRotation), maxRotation) / 180 * Math.PI;
    const gammaRotation = Math.min(Math.max(event.gamma, -maxRotation), maxRotation) / 180 * Math.PI;
    
    mouseX = gammaRotation * 10;
    mouseY = betaRotation * 10;
  }
}

function onWindowResize() {
  windowHalfX = window.innerWidth / 2;
  windowHalfY = window.innerHeight / 2;
  
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  
  renderer.setSize(window.innerWidth, window.innerHeight);
}

function updateTerminalTyping(deltaTime) {
  if (!terminalGroup) return;
  
  // Check if skills array is valid
  if (!terminalSkills || terminalSkills.length === 0 || currentSkill >= terminalSkills.length) {
    console.warn('Terminal skills not properly initialized');
    return;
  }
  
  // Get font loader
  const fontLoader = new THREE.FontLoader();
  
  lastTypingTime += deltaTime * 1000;
  
  if (isTyping) {
    // Typing animation
    if (lastTypingTime > typingSpeed) {
      lastTypingTime = 0;
      
      if (typedText.length < terminalSkills[currentSkill].length) {
        typedText += terminalSkills[currentSkill][typedText.length];
        
        fontLoader.load('https://threejs.org/examples/fonts/helvetiker_regular.typeface.json', function(font) {
          createTerminalText(font, typedText);
        });
      } else {
        isTyping = false;
        setTimeout(() => {
          isTyping = false;
          typedText = "";
          // Make sure we stay within bounds
          currentSkill = (currentSkill + 1) % terminalSkills.length;
          
          // Create particle explosion effect on skill change
          createParticleExplosion();
          
          setTimeout(() => {
            isTyping = true;
            // Make sure we stay within bounds
            currentSkill = (currentSkill + 1) % terminalSkills.length;
          }, 1000);
        }, 2000);
      }
    }
  }
}

function createParticleExplosion() {
  // Create colorful explosion particles
  const colors = themeIsDark ? 
    [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple, COLORS.dark.neonGreen, COLORS.dark.neonOrange] : 
    [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple, COLORS.light.neonGreen, COLORS.light.neonOrange];
  
  const particlesGeometry = new THREE.BufferGeometry();
  const particlesMaterial = new THREE.PointsMaterial({
    size: 0.5,
    vertexColors: true,
    transparent: true,
    opacity: 1
  });
  
  const particleCount = 50; // Reduced from 150 for better performance
  const positions = new Float32Array(particleCount * 3);
  const particleColors = new Float32Array(particleCount * 3);
  const velocities = [];
  
  for (let i = 0; i < particleCount; i++) {
    // Start particles from terminal position
    positions[i * 3] = -11 + (Math.random() - 0.5) * 10;
    positions[i * 3 + 1] = 3 + (Math.random() - 0.5) * 3;
    positions[i * 3 + 2] = 0.6 + (Math.random() - 0.5);
    
    // Random velocity
    velocities.push({
      x: (Math.random() - 0.5) * 1.5,
      y: (Math.random() - 0.5) * 1.5,
      z: (Math.random() - 0.5) * 0.5
    });
    
    // Random color from palette
    const color = new THREE.Color(colors[Math.floor(Math.random() * colors.length)]);
    particleColors[i * 3] = color.r;
    particleColors[i * 3 + 1] = color.g;
    particleColors[i * 3 + 2] = color.b;
  }
  
  particlesGeometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
  particlesGeometry.setAttribute('color', new THREE.BufferAttribute(particleColors, 3));
  
  const particles = new THREE.Points(particlesGeometry, particlesMaterial);
  particles.userData.velocities = velocities;
  particles.userData.lifespan = 1.5;
  particles.userData.isParticleExplosion = true;
  
  scene.add(particles);
}

function updateParticles(deltaTime) {
  scene.children.forEach(child => {
    if (child.userData.isParticleExplosion) {
      const positions = child.geometry.attributes.position.array;
      const velocities = child.userData.velocities;
      
      // Update particle positions
      for (let i = 0; i < velocities.length; i++) {
        positions[i * 3] += velocities[i].x;
        positions[i * 3 + 1] += velocities[i].y;
        positions[i * 3 + 2] += velocities[i].z;
        
        // Add gravity
        velocities[i].y -= 0.02;
        
        // Add drag
        velocities[i].x *= 0.98;
        velocities[i].y *= 0.98;
        velocities[i].z *= 0.98;
      }
      
      child.geometry.attributes.position.needsUpdate = true;
      
      // Fade out and remove
      child.userData.lifespan -= deltaTime;
      child.material.opacity = Math.max(0, child.userData.lifespan / 1.5);
      
      if (child.userData.lifespan <= 0) {
        scene.remove(child);
      }
    }
  });
}

function updateLightBeams(deltaTime) {
  scene.children.forEach(child => {
    if (child.userData.type === 'lightBeam') {
      const time = Date.now() * 0.001;
      
      // Rotate around Y axis
      child.rotation.y += deltaTime * child.userData.rotationSpeed;
      
      // Pulse opacity
      child.material.opacity = 0.2 + Math.sin(time * child.userData.pulseSpeed) * 0.15;
    }
  });
}

function updateDynamicGrid(deltaTime) {
  if (!gridMesh) return;
  
  const positions = gridMesh.geometry.attributes.position.array;
  const time = Date.now() * 0.0005;
  
  // Create a more dynamic wave effect
  for (let i = 0; i < positions.length; i += 3) {
    const x = gridMesh.geometry.attributes.position.array[i];
    const z = gridMesh.geometry.attributes.position.array[i + 2];
    const distance = Math.sqrt(x * x + z * z);
    
    // Create ripple effect from center
    positions[i + 1] = Math.sin(distance * 0.05 + time) * 
                      Math.sin(x * 0.05 + time * 0.7) * 
                      Math.sin(z * 0.05 + time * 0.5) * 5;
  }
  
  gridMesh.geometry.attributes.position.needsUpdate = true;
}

function animate(timestamp) {
  animationFrameId = requestAnimationFrame(animate);
  
  // Skip frames on low-performance devices
  if (isLowPerformanceDevice) {
    frameCount++;
    if (frameCount % 2 !== 0) return; // Render only every other frame
  }
  
  // Calculate FPS
  if (timestamp) {
    const elapsed = timestamp - lastFrameTime;
    if (elapsed >= 1000) { // Update every second
      lastFrameTime = timestamp;
    }
  }
  
  // Force garbage collection occasionally by nullifying and recreating objects
  if (timestamp - lastGcTime > 10000) { // Every 10 seconds
    lastGcTime = timestamp;
  }
  
  // Update controls if available
  if (controls && typeof controls.update === 'function') {
    controls.update();
  }
  
  const deltaTime = clock.getDelta();
  
  // Smooth camera movement using lerp factor
  camera.position.x += (mouseX - camera.position.x) * 0.05;
  camera.position.y += (-mouseY - camera.position.y) * 0.05;
  camera.lookAt(scene.position);
  
  // Animate terminal typing
  updateTerminalTyping(deltaTime);
  
  // Float terminal with more complex motion
  if (terminalGroup) {
    const time = Date.now() * 0.001;
    terminalGroup.position.y = 8 + Math.sin(time) * 0.8 + Math.sin(time * 1.5) * 0.4;
    terminalGroup.rotation.z = Math.sin(time * 0.5) * 0.02;
  }
  
  // Update particles
  updateParticles(deltaTime);
  
  // Update light beams
  updateLightBeams(deltaTime);
  
  // Update dynamic grid
  updateDynamicGrid(deltaTime);
  
  renderer.render(scene, camera);
}

// Function to stop rendering when tab is not visible
function handleVisibilityChange() {
  if (document.hidden) {
    // Stop animation loop when tab is not visible
    cancelAnimationFrame(animationFrameId);
  } else {
    // Resume animation loop when tab becomes visible again
    animationFrameId = requestAnimationFrame(animate);
  }
}

document.addEventListener('visibilitychange', handleVisibilityChange);

document.addEventListener('DOMContentLoaded', () => {
  console.log('DOM loaded in 3d-background.js');
  // Check for theme
  themeIsDark = !document.body.hasAttribute('data-theme') || 
               document.body.getAttribute('data-theme') !== 'light';
  
  init();
  animate();
});
