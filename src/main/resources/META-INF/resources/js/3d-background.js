// Refined Three.js setup for a professional portfolio
let camera, scene, renderer, controls;
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
let typingSpeed = 120; // Slightly slower for more professional feel
let lastTypingTime = 0;
let themeIsDark = true;

// Refined, professional color palette
const COLORS = {
  // Dark theme colors - more refined
  dark: {
    base: 0x0a0a14,           // Deep space blue base
    cyberCyan: 0x00c9db,      // Professional subdued cyan
    neonPink: 0xd63384,       // Professional muted pink
    neonPurple: 0x7952b3,     // Professional muted purple
    neonGreen: 0x00b894,      // Professional muted green
    neonOrange: 0xe17055,     // Professional muted orange
    neonBlue: 0x0984e3,       // Professional blue
    neonYellow: 0xfdcb6e      // Professional muted yellow
  },
  // Light theme colors - more refined
  light: {
    base: 0xf8fafc,           // Off-white base
    cyberCyan: 0x0891b2,      // Professional teal
    neonPink: 0xbe185d,       // Professional rose
    neonPurple: 0x6d28d9,     // Professional violet
    neonGreen: 0x059669,      // Professional emerald
    neonOrange: 0xea580c,     // Professional amber
    neonBlue: 0x2563eb,       // Professional blue
    neonYellow: 0xd97706      // Professional yellow
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
  camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
  camera.position.z = 35; // Moved back for more subtle effect

  // Add OrbitControls with restricted polar angle - with error handling
  try {
    if (typeof THREE.OrbitControls === 'function') {
      controls = new THREE.OrbitControls(camera, canvas);
      controls.enableZoom = false;
      controls.enablePan = false;
      controls.maxPolarAngle = Math.PI / 2.5; // More limited vertical movement
      controls.minPolarAngle = Math.PI / 3.5; // More limited vertical movement
      controls.enableDamping = true; // Smoother movement
      controls.dampingFactor = 0.1; // Less aggressive damping
      controls.rotateSpeed = 0.5; // Slower rotation
      controls.autoRotate = true;
      controls.autoRotateSpeed = 0.3; // Slower auto rotation
      controls.update();
      console.log('OrbitControls initialized successfully');
    } else {
      console.warn('THREE.OrbitControls is not available');
    }
  } catch (error) {
    console.error('Error initializing OrbitControls:', error);
  }

  // Create refined grid
  createRefinedGrid();

  // Create floating terminal with professional design
  createProfessionalTerminal();
  console.log('Created professional terminal:', terminalGroup);

  // Add subtle star particles
  addSubtleStars();

  // Add subtle light beams
  addSubtleLightBeams();

  // Renderer setup
  renderer = new THREE.WebGLRenderer({
    canvas: canvas,
    antialias: true,
    alpha: true
  });
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));

  // Mouse move event - less aggressive movement
  document.addEventListener('mousemove', onDocumentMouseMove);

  // Device orientation for mobile - more subtle tilt
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
  initRefinedProjectModels();
}

function updateSceneColors() {
  // Set colors based on current theme
  const colors = themeIsDark ? COLORS.dark : COLORS.light;
  scene.background = new THREE.Color(colors.base);
  scene.fog = new THREE.FogExp2(colors.base, 0.01); // More subtle fog

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
                          colors.neonGreen, colors.neonBlue][Math.floor(Math.random() * 5)];
      object.material.color.set(randomColor);
    }
  });
}

function createRefinedGrid() {
  // Create more professional grid with subtle styling
  const gridSize = 200;
  const gridDivisions = 15; // Reduced divisions for cleaner look

  // Main grid
  const gridGeometry = new THREE.PlaneGeometry(gridSize, gridSize, gridDivisions, gridDivisions);
  const gridMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    wireframe: true,
    transparent: true,
    opacity: 0.15 // Much more subtle grid
  });

  gridMesh = new THREE.Mesh(gridGeometry, gridMaterial);
  gridMesh.rotation.x = -Math.PI / 2;
  gridMesh.position.y = -35;
  gridMesh.userData.type = 'grid';
  scene.add(gridMesh);

  // Secondary grid for added depth
  const gridGeometry2 = new THREE.PlaneGeometry(gridSize * 2, gridSize * 2, gridDivisions / 2, gridDivisions / 2);
  const gridMaterial2 = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.neonPink : COLORS.light.neonPink,
    wireframe: true,
    transparent: true,
    opacity: 0.05 // Even more subtle secondary grid
  });

  const gridMesh2 = new THREE.Mesh(gridGeometry2, gridMaterial2);
  gridMesh2.rotation.x = -Math.PI / 2;
  gridMesh2.position.y = -40;
  gridMesh2.userData.type = 'grid';
  scene.add(gridMesh2);

  // Add ambient and directional light
  const ambientLight = new THREE.AmbientLight(0x404040, 0.6);
  scene.add(ambientLight);

  const directionalLight = new THREE.DirectionalLight(
    themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    0.6 // Less intense light
  );
  directionalLight.position.set(0, 50, 30);
  scene.add(directionalLight);
}

function createProfessionalTerminal() {
  // Create a more refined, professional terminal
  terminalGroup = new THREE.Group();
  scene.add(terminalGroup);

  // Terminal base - more subtle and professional
  const baseGeometry = new THREE.BoxGeometry(28, 14, 0.5);
  const baseMaterial = new THREE.MeshPhongMaterial({
    color: themeIsDark ? COLORS.dark.base : COLORS.light.base,
    transparent: true,
    opacity: 0.85,
    shininess: 60, // Less intense shine
    specular: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    emissive: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    emissiveIntensity: 0.1 // Much more subtle glow
  });

  const base = new THREE.Mesh(baseGeometry, baseMaterial);
  base.userData.type = 'terminal';
  terminalGroup.add(base);

  // Terminal frame with more subtle glow
  const frameGeometry = new THREE.BoxGeometry(28.2, 14.2, 0.6);
  const frameMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    transparent: true,
    opacity: 0.3 // More subtle frame
  });

  const frame = new THREE.Mesh(frameGeometry, frameMaterial);
  frame.userData.type = 'terminal';
  terminalGroup.add(frame);

  // Terminal header bar - more subtle
  const headerGeometry = new THREE.BoxGeometry(28, 1.5, 0.6);
  const headerMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.neonPink : COLORS.light.neonPink,
    transparent: true,
    opacity: 0.4 // More subtle header
  });

  const header = new THREE.Mesh(headerGeometry, headerMaterial);
  header.position.y = 6.25;
  header.userData.type = 'terminal';
  terminalGroup.add(header);

  // Add decorative elements to the terminal - more professional
  // Data lines with subtle appearance
  for (let i = 0; i < 4; i++) {
    const lineGeometry = new THREE.BoxGeometry(24, 0.1, 0.1);
    const lineMaterial = new THREE.MeshBasicMaterial({
      color: themeIsDark ?
        [COLORS.dark.cyberCyan, COLORS.dark.neonPink][i % 2] :
        [COLORS.light.cyberCyan, COLORS.light.neonPink][i % 2],
      transparent: true,
      opacity: 0.5 - (i * 0.1) // Fading opacity for depth
    });

    const line = new THREE.Mesh(lineGeometry, lineMaterial);
    line.position.y = 3 - i * 2;
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
    const titleWidth = 12;
    const titleGeometry = new THREE.BoxGeometry(titleWidth, 0.8, 0.1);
    const titleMaterial = new THREE.MeshBasicMaterial({
      color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
      opacity: 0.9,
      transparent: true
    });

    const titleMesh = new THREE.Mesh(titleGeometry, titleMaterial);
    titleMesh.position.set(-7, 6.25, 0.6);
    titleMesh.userData.type = 'text';
    titleMesh.userData.text = 'TERMINAL';
    terminalGroup.add(titleMesh);

    // Create prompt
    const promptGeometry = new THREE.BoxGeometry(0.8, 0.8, 0.1);
    const promptMaterial = new THREE.MeshBasicMaterial({
      color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
      opacity: 0.9,
      transparent: true
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
  const textWidth = text.length * 0.7;
  const textGeometry = new THREE.BoxGeometry(textWidth, 0.8, 0.1);

  const textMaterial = new THREE.MeshBasicMaterial({
    color: themeIsDark ? COLORS.dark.cyberCyan : COLORS.light.cyberCyan,
    opacity: 0.9,
    transparent: true
  });

  const textMesh = new THREE.Mesh(textGeometry, textMaterial);
  textMesh.position.set(-12 + textWidth/2, 3, 0.6);
  textMesh.userData.isText = true;
  textMesh.userData.type = 'text';
  textMesh.userData.text = text; // Store the text

  terminalGroup.add(textMesh);
}

function addSubtleStars() {
  // Create more subtle star field
  const starsGeometry = new THREE.BufferGeometry();
  const starsMaterial = new THREE.PointsMaterial({
    color: 0xffffff,
    size: 0.15, // Smaller stars
    transparent: true,
    opacity: 0.6 // More subtle stars
  });

  const starsVertices = [];
  const starsColors = [];

  // Color array with more subtle colors
  const colorOptions = [
    new THREE.Color(0xffffff), // white
    new THREE.Color(COLORS.dark.cyberCyan),
    new THREE.Color(COLORS.dark.neonPink),
    new THREE.Color(COLORS.dark.neonPurple)
  ];

  for (let i = 0; i < 800; i++) { // Reduced star count
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

function addSubtleLightBeams() {
  // Add more subtle, professional light beams
  const beamCount = 3; // Fewer beams
  const colors = themeIsDark ?
    [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple] :
    [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple];

  for (let i = 0; i < beamCount; i++) {
    const angle = (i / beamCount) * Math.PI * 2;
    const distance = 80 + Math.random() * 20;

    const x = Math.cos(angle) * distance;
    const z = Math.sin(angle) * distance;

    const height = 100 + Math.random() * 30;
    const width = 0.5 + Math.random() * 1; // Thinner beams

    const beamGeometry = new THREE.BoxGeometry(width, height, width);
    const beamMaterial = new THREE.MeshBasicMaterial({
      color: colors[i % colors.length],
      transparent: true,
      opacity: 0.15 + Math.random() * 0.15 // Much more subtle opacity
    });

    const beam = new THREE.Mesh(beamGeometry, beamMaterial);
    beam.position.set(x, height/2 - 30, z);
    beam.userData.type = 'lightBeam';
    beam.userData.rotationSpeed = 0.05 + Math.random() * 0.15; // Slower rotation
    beam.userData.pulseSpeed = 0.3 + Math.random() * 0.3; // Slower pulse
    scene.add(beam);
  }
}

function initRefinedProjectModels() {
  // Find all model containers
  console.log('Initializing refined project models');
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
      const modelCamera = new THREE.PerspectiveCamera(45, container.clientWidth / container.clientHeight, 0.1, 1000);
      modelCamera.position.z = 5;

      // Create a renderer
      const modelRenderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
      modelRenderer.setSize(container.clientWidth, container.clientHeight);
      container.appendChild(modelRenderer.domElement);

      // Create more professional geometries based on index
      let geometry;
      switch(index % 4) {
      case 0:
        // Refined sphere
        geometry = new THREE.SphereGeometry(1.4, 32, 32);
        break;
      case 1:
        // Refined torus
        geometry = new THREE.TorusGeometry(1.1, 0.4, 24, 36);
        break;
      case 2:
        // Refined icosahedron
        geometry = new THREE.IcosahedronGeometry(1.3, 1);
        break;
      case 3:
        // Refined octahedron
        geometry = new THREE.OctahedronGeometry(1.3, 1);
        break;
    }

    // Create material with more refined look
    const mainColor = themeIsDark ?
      [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple, COLORS.dark.neonBlue][index % 4] :
      [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple, COLORS.light.neonBlue][index % 4];

    const material = new THREE.MeshStandardMaterial({
      color: mainColor,
      metalness: 0.7,  // Less extreme metalness
      roughness: 0.3,  // Slightly rougher for more realism
      transparent: true,
      opacity: 0.85
    });

    const mesh = new THREE.Mesh(geometry, material);
    modelScene.add(mesh);

    // Add subtle wireframe overlay
    const wireframeGeometry = new THREE.WireframeGeometry(geometry);
    const wireframeMaterial = new THREE.LineBasicMaterial({
      color: 0xffffff,
      transparent: true,
      opacity: 0.2 // More subtle wireframe
    });

    const wireframe = new THREE.LineSegments(wireframeGeometry, wireframeMaterial);
    modelScene.add(wireframe);

    // Add ambient and point lights
    const ambientLight = new THREE.AmbientLight(0x404040, 0.6);
    modelScene.add(ambientLight);

    const pointLight = new THREE.PointLight(mainColor, 0.8, 10);
    pointLight.position.set(3, 3, 3);
    modelScene.add(pointLight);

    // Animation function with more subtle, professional motion
    function animateModel() {
      requestAnimationFrame(animateModel);

      const time = Date.now() * 0.0008; // Slower time scale

      // More subtle rotating motion
      mesh.rotation.x = time * 0.3;
      mesh.rotation.y = time * 0.4;
      wireframe.rotation.x = time * 0.3;
      wireframe.rotation.y = time * 0.4;

      // More subtle floating motion
      mesh.position.y = Math.sin(time * 0.7) * 0.15;
      wireframe.position.y = Math.sin(time * 0.7) * 0.15;

      const scale = 1 + Math.sin(time * 1.2) * 0.05; // Less extreme pulsing
      mesh.scale.set(scale, scale, scale);
      wireframe.scale.set(scale, scale, scale);

      // Adjust light intensity more subtly
      pointLight.intensity = 0.8 + Math.sin(time * 1.5) * 0.2;

      modelRenderer.render(modelScene, modelCamera);
    }

    animateModel();
    } catch (error) {
      console.error('Error initializing 3D model:', error);
    }
  });
}

function onDocumentMouseMove(event) {
  // More subtle mouse movement effect
  mouseX = (event.clientX - windowHalfX) / 200; // Reduced sensitivity
  mouseY = (event.clientY - windowHalfY) / 200; // Reduced sensitivity
}

function onDeviceOrientation(event) {
  if (event.beta && event.gamma) {
    // Limit rotation to 5 degrees (more subtle)
    const maxRotation = 5;
    const betaRotation = Math.min(Math.max(event.beta - 90, -maxRotation), maxRotation) / 180 * Math.PI;
    const gammaRotation = Math.min(Math.max(event.gamma, -maxRotation), maxRotation) / 180 * Math.PI;

    mouseX = gammaRotation * 5; // Reduced sensitivity
    mouseY = betaRotation * 5; // Reduced sensitivity
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

          // Create subtle particle effect on skill change
          createSubtleParticleEffect();

          setTimeout(() => {
            isTyping = true;
            // Make sure we stay within bounds
            currentSkill = (currentSkill + 1) % terminalSkills.length;
          }, 1500); // Longer pause between skills
        }, 3000); // Longer display time
      }
    }
  }
}

function createSubtleParticleEffect() {
  // Create subtle, professional particle effect
  const colors = themeIsDark ?
    [COLORS.dark.cyberCyan, COLORS.dark.neonPink, COLORS.dark.neonPurple] :
    [COLORS.light.cyberCyan, COLORS.light.neonPink, COLORS.light.neonPurple];

  const particlesGeometry = new THREE.BufferGeometry();
  const particlesMaterial = new THREE.PointsMaterial({
    size: 0.3, // Smaller particles
    vertexColors: true,
    transparent: true,
    opacity: 0.7
  });

  const particleCount = 30; // Fewer particles
  const positions = new Float32Array(particleCount * 3);
  const particleColors = new Float32Array(particleCount * 3);
  const velocities = [];

  for (let i = 0; i < particleCount; i++) {
    // Start particles from terminal position
    positions[i * 3] = -11 + (Math.random() - 0.5) * 8;
    positions[i * 3 + 1] = 3 + (Math.random() - 0.5) * 2;
    positions[i * 3 + 2] = 0.6 + (Math.random() - 0.5) * 0.5;

    // Random velocity
    velocities.push({
      x: (Math.random() - 0.5) * 0.8, // Slower particles
      y: (Math.random() - 0.5) * 0.8,
      z: (Math.random() - 0.5) * 0.3
    });

    // Random color from refined palette
    const color = new THREE.Color(colors[Math.floor(Math.random() * colors.length)]);
    particleColors[i * 3] = color.r;
    particleColors[i * 3 + 1] = color.g;
    particleColors[i * 3 + 2] = color.b;
  }

  particlesGeometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
  particlesGeometry.setAttribute('color', new THREE.BufferAttribute(particleColors, 3));

  const particles = new THREE.Points(particlesGeometry, particlesMaterial);
  particles.userData.velocities = velocities;
  particles.userData.lifespan = 2.0; // Longer lifespan
  particles.userData.isParticleExplosion = true;

  scene.add(particles);
}

function updateParticles(deltaTime) {
  scene.children.forEach(child => {
    if (child.userData.isParticleExplosion) {
      const positions = child.geometry.attributes.position.array;
      const velocities = child.userData.velocities;

      // Update particle positions with more subtle movement
      for (let i = 0; i < velocities.length; i++) {
        positions[i * 3] += velocities[i].x * 0.7; // Slower movement
        positions[i * 3 + 1] += velocities[i].y * 0.7;
        positions[i * 3 + 2] += velocities[i].z * 0.7;

        // Add subtle gravity
        velocities[i].y -= 0.01;

        // Add subtle drag
        velocities[i].x *= 0.99;
        velocities[i].y *= 0.99;
        velocities[i].z *= 0.99;
      }

      child.geometry.attributes.position.needsUpdate = true;

      // Fade out and remove
      child.userData.lifespan -= deltaTime;
      child.material.opacity = Math.max(0, child.userData.lifespan / 2.0) * 0.7; // More subtle fade

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

      // Rotate around Y axis - slower
      child.rotation.y += deltaTime * child.userData.rotationSpeed * 0.5;

      // Pulse opacity - more subtle
      child.material.opacity = 0.1 + Math.sin(time * child.userData.pulseSpeed) * 0.05;
    }
  });
}

function updateDynamicGrid(deltaTime) {
  if (!gridMesh) return;

  const positions = gridMesh.geometry.attributes.position.array;
  const time = Date.now() * 0.0003; // Slower time scale

  // Create a more subtle wave effect
  for (let i = 0; i < positions.length; i += 3) {
    const x = gridMesh.geometry.attributes.position.array[i];
    const z = gridMesh.geometry.attributes.position.array[i + 2];
    const distance = Math.sqrt(x * x + z * z);

    // Subtle ripple effect from center
    positions[i + 1] = Math.sin(distance * 0.03 + time) *
                      Math.sin(x * 0.03 + time * 0.5) *
                      Math.sin(z * 0.03 + time * 0.3) * 2; // Reduced amplitude
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

  // Smoother camera movement using smaller lerp factor
  camera.position.x += (mouseX - camera.position.x) * 0.03; // More subtle movement
  camera.position.y += (-mouseY - camera.position.y) * 0.03; // More subtle movement
  camera.lookAt(scene.position);

  // Animate terminal typing
  updateTerminalTyping(deltaTime);

  // Float terminal with more subtle motion
  if (terminalGroup) {
    const time = Date.now() * 0.0007; // Slower time scale
    terminalGroup.position.y = 8 + Math.sin(time) * 0.5 + Math.sin(time * 1.3) * 0.2; // Reduced amplitude
    terminalGroup.rotation.z = Math.sin(time * 0.3) * 0.01; // Reduced rotation
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