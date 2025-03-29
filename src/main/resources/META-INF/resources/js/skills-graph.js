// 3D Force-Directed Graph for Skills Matrix

// Cyberpunk color palette - different variable name to avoid conflict
const GRAPH_COLORS = {
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

// Skills data with categories
const skills = [
  { id: "kotlin", name: "Kotlin", category: "language", level: 0.9 },
  { id: "java", name: "Java", category: "language", level: 0.85 },
  { id: "javascript", name: "JavaScript", category: "language", level: 0.8 },
  { id: "typescript", name: "TypeScript", category: "language", level: 0.75 },
  { id: "quarkus", name: "Quarkus", category: "framework", level: 0.9 },
  { id: "spring", name: "Spring", category: "framework", level: 0.8 },
  { id: "react", name: "React", category: "frontend", level: 0.8 },
  { id: "threejs", name: "Three.js", category: "frontend", level: 0.7 },
  { id: "postgresql", name: "PostgreSQL", category: "database", level: 0.85 },
  { id: "mongodb", name: "MongoDB", category: "database", level: 0.7 },
  { id: "docker", name: "Docker", category: "devops", level: 0.8 },
  { id: "kubernetes", name: "Kubernetes", category: "devops", level: 0.7 },
  { id: "aws", name: "AWS", category: "cloud", level: 0.75 },
  { id: "azure", name: "Azure", category: "cloud", level: 0.7 },
  { id: "rest", name: "REST APIs", category: "backend", level: 0.9 },
  { id: "graphql", name: "GraphQL", category: "backend", level: 0.7 }
];

// Links between skills (relationships)
const links = [
  { source: "kotlin", target: "quarkus", strength: 0.8 },
  { source: "kotlin", target: "java", strength: 0.7 },
  { source: "java", target: "spring", strength: 0.9 },
  { source: "javascript", target: "typescript", strength: 0.8 },
  { source: "typescript", target: "react", strength: 0.9 },
  { source: "javascript", target: "react", strength: 0.8 },
  { source: "javascript", target: "threejs", strength: 0.7 },
  { source: "quarkus", target: "rest", strength: 0.9 },
  { source: "spring", target: "rest", strength: 0.8 },
  { source: "quarkus", target: "postgresql", strength: 0.7 },
  { source: "spring", target: "postgresql", strength: 0.7 },
  { source: "rest", target: "graphql", strength: 0.5 },
  { source: "docker", target: "kubernetes", strength: 0.9 },
  { source: "aws", target: "docker", strength: 0.7 },
  { source: "azure", target: "docker", strength: 0.7 },
  { source: "aws", target: "kubernetes", strength: 0.7 },
  { source: "azure", target: "kubernetes", strength: 0.7 }
];

// Main initialization function
function initSkillsGraph() {
  const container = document.getElementById('skills-matrix');
  if (!container) return;
  
  // Check theme
  const themeIsDark = !document.body.hasAttribute('data-theme') || 
                     document.body.getAttribute('data-theme') !== 'light';
  
  // Get current theme colors
  const colors = themeIsDark ? GRAPH_COLORS.dark : GRAPH_COLORS.light;
  
  // Scene setup
  const scene = new THREE.Scene();
  scene.background = new THREE.Color(colors.base);
  
  // Camera setup
  const camera = new THREE.PerspectiveCamera(75, container.clientWidth / container.clientHeight, 0.1, 1000);
  camera.position.z = 150;
  
  // Renderer setup
  const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
  renderer.setSize(container.clientWidth, container.clientHeight);
  container.appendChild(renderer.domElement);
  
  // Controls for interactive rotation
  const controls = new THREE.OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;
  controls.dampingFactor = 0.25;
  controls.enableZoom = true;
  controls.autoRotate = true;
  controls.autoRotateSpeed = 0.5;
  
  // Add ambient light
  const ambientLight = new THREE.AmbientLight(0x404040, 0.5);
  scene.add(ambientLight);
  
  // Add directional light
  const directionalLight = new THREE.DirectionalLight(0xffffff, 1);
  directionalLight.position.set(1, 1, 1);
  scene.add(directionalLight);
  
  // Node objects with PBR materials as specified (liquid metal)
  const meshes = skills.map(skill => {
    // Scale node size by skill level
    const radius = 3 + skill.level * 3;
    
    // Create sphere geometry
    const geometry = new THREE.SphereGeometry(radius, 32, 32);
    
    // Create PBR material with specified properties
    // roughness: 0.2, metalness: 0.9 as specified in design
    const material = new THREE.MeshStandardMaterial({
      color: getColorForCategory(skill.category, colors),
      metalness: 0.9,
      roughness: 0.2,
      transparent: true,
      opacity: 0.8,
      emissive: getColorForCategory(skill.category, colors),
      emissiveIntensity: 0.3
    });
    
    // Create mesh
    const mesh = new THREE.Mesh(geometry, material);
    mesh.userData = { id: skill.id, name: skill.name, category: skill.category };
    
    // Add to scene
    scene.add(mesh);
    return mesh;
  });
  
  // Create node objects for simulation
  const nodes = skills.map((skill, i) => ({
    id: skill.id,
    mesh: meshes[i],
    x: Math.random() * 100 - 50,
    y: Math.random() * 100 - 50,
    z: Math.random() * 100 - 50,
    fx: null,
    fy: null,
    fz: null
  }));
  
  // Create a map for quick lookup
  const nodeMap = {};
  nodes.forEach(node => {
    nodeMap[node.id] = node;
  });
  
  // Create links between nodes
  const lineObjects = links.map(link => {
    const sourceNode = nodeMap[link.source];
    const targetNode = nodeMap[link.target];
    
    if (!sourceNode || !targetNode) return null;
    
    // Create line geometry
    const geometry = new THREE.BufferGeometry();
    const positions = new Float32Array(6); // 2 points, 3 values (x,y,z) each
    geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
    
    // Create line material with glow
    const material = new THREE.LineBasicMaterial({
      color: colors.cyberCyan,
      transparent: true,
      opacity: link.strength * 0.7,
      linewidth: 1
    });
    
    // Create line
    const line = new THREE.Line(geometry, material);
    line.userData = {
      source: sourceNode,
      target: targetNode,
      strength: link.strength
    };
    
    // Add to scene
    scene.add(line);
    return line;
  }).filter(Boolean);
  
  // Update line positions
  function updateLines() {
    lineObjects.forEach(line => {
      const source = line.userData.source;
      const target = line.userData.target;
      
      // Update positions array
      const positions = line.geometry.attributes.position.array;
      positions[0] = source.x;
      positions[1] = source.y;
      positions[2] = source.z;
      positions[3] = target.x;
      positions[4] = target.y;
      positions[5] = target.z;
      
      line.geometry.attributes.position.needsUpdate = true;
    });
  }
  
  // Simulation using D3-force
  // Since we can't directly use d3-force-3d, we'll implement a simplified version
  function simulateForces() {
    // Apply attraction forces between linked nodes
    links.forEach(link => {
      const source = nodeMap[link.source];
      const target = nodeMap[link.target];
      
      if (!source || !target) return;
      
      // Vector from source to target
      const dx = target.x - source.x;
      const dy = target.y - source.y;
      const dz = target.z - source.z;
      
      // Distance
      const distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
      
      // Target distance based on link strength (stronger = closer)
      const targetDistance = 50 * (1 - link.strength);
      
      // Force strength
      const force = (distance - targetDistance) * 0.01 * link.strength;
      
      // Apply force
      if (distance > 0) {
        const fx = dx / distance * force;
        const fy = dy / distance * force;
        const fz = dz / distance * force;
        
        source.x += fx;
        source.y += fy;
        source.z += fz;
        
        target.x -= fx;
        target.y -= fy;
        target.z -= fz;
      }
    });
    
    // Apply repulsion forces between all nodes
    for (let i = 0; i < nodes.length; i++) {
      for (let j = i + 1; j < nodes.length; j++) {
        const nodeA = nodes[i];
        const nodeB = nodes[j];
        
        // Vector from A to B
        const dx = nodeB.x - nodeA.x;
        const dy = nodeB.y - nodeA.y;
        const dz = nodeB.z - nodeA.z;
        
        // Distance
        const distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        
        // Repulsion force (inverse square law)
        const repulsion = 100 / (distance * distance);
        
        // Apply force
        if (distance > 0) {
          const fx = dx / distance * repulsion;
          const fy = dy / distance * repulsion;
          const fz = dz / distance * repulsion;
          
          nodeA.x -= fx;
          nodeA.y -= fy;
          nodeA.z -= fz;
          
          nodeB.x += fx;
          nodeB.y += fy;
          nodeB.z += fz;
        }
      }
    }
    
    // Apply center gravity
    nodes.forEach(node => {
      node.x += (0 - node.x) * 0.001;
      node.y += (0 - node.y) * 0.001;
      node.z += (0 - node.z) * 0.001;
      
      // Update mesh position
      node.mesh.position.set(node.x, node.y, node.z);
    });
    
    // Update lines
    updateLines();
  }
  
  // Handle hover interactions
  const raycaster = new THREE.Raycaster();
  const mouse = new THREE.Vector2();
  let hoveredNode = null;
  
  container.addEventListener('mousemove', event => {
    // Calculate mouse position
    const rect = renderer.domElement.getBoundingClientRect();
    mouse.x = ((event.clientX - rect.left) / rect.width) * 2 - 1;
    mouse.y = -((event.clientY - rect.top) / rect.height) * 2 + 1;
    
    // Raycast
    raycaster.setFromCamera(mouse, camera);
    const intersects = raycaster.intersectObjects(meshes);
    
    // Reset previously hovered node
    if (hoveredNode) {
      hoveredNode.scale.set(1, 1, 1);
      hoveredNode.material.emissiveIntensity = 0.3;
    }
    
    // Handle new hover
    if (intersects.length > 0) {
      hoveredNode = intersects[0].object;
      hoveredNode.scale.set(1.2, 1.2, 1.2);
      hoveredNode.material.emissiveIntensity = 0.7;
      
      // Highlight connected lines by making them brighter
      lineObjects.forEach(line => {
        if (line.userData.source.mesh === hoveredNode || line.userData.target.mesh === hoveredNode) {
          // Lines thicken on hover as specified in the design
          line.material.opacity = 1;
        } else {
          line.material.opacity = 0.2;
        }
      });
      
      // Show node info
      showNodeInfo(hoveredNode.userData);
    } else {
      hoveredNode = null;
      hideNodeInfo();
      
      // Reset all lines
      lineObjects.forEach(line => {
        line.material.opacity = line.userData.strength * 0.7;
      });
    }
  });
  
  // Animation loop
  function animate() {
    requestAnimationFrame(animate);
    
    // Update physics simulation
    simulateForces();
    
    // Update controls
    controls.update();
    
    // Render scene
    renderer.render(scene, camera);
  }
  
  // Start animation
  animate();
  
  // Handle window resize
  window.addEventListener('resize', () => {
    camera.aspect = container.clientWidth / container.clientHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(container.clientWidth, container.clientHeight);
  });
  
  // Handle theme changes
  document.getElementById('theme-toggle').addEventListener('click', () => {
    setTimeout(() => {
      const isDarkTheme = !document.body.hasAttribute('data-theme') || 
                         document.body.getAttribute('data-theme') !== 'light';
      const themeColors = isDarkTheme ? GRAPH_COLORS.dark : GRAPH_COLORS.light;
      
      // Update scene background
      scene.background = new THREE.Color(themeColors.base);
      
      // Update node colors
      meshes.forEach(mesh => {
        const category = mesh.userData.category;
        const color = getColorForCategory(category, themeColors);
        mesh.material.color.set(color);
        mesh.material.emissive.set(color);
      });
      
      // Update line colors
      lineObjects.forEach(line => {
        line.material.color.set(themeColors.cyberCyan);
      });
    }, 100);
  });
}

// Helper functions for node info display
function showNodeInfo(data) {
  let infoPanel = document.getElementById('skill-info-panel');
  if (!infoPanel) {
    infoPanel = document.createElement('div');
    infoPanel.id = 'skill-info-panel';
    infoPanel.style.position = 'absolute';
    infoPanel.style.top = '10px';
    infoPanel.style.right = '10px';
    infoPanel.style.padding = '15px';
    infoPanel.style.backgroundColor = 'var(--surface)';
    infoPanel.style.backdropFilter = 'blur(12px)';
    infoPanel.style.border = '2px solid var(--cyber-cyan)';
    infoPanel.style.borderRadius = '5px';
    infoPanel.style.color = 'var(--text)';
    infoPanel.style.fontFamily = 'Space Grotesk, sans-serif';
    infoPanel.style.zIndex = '1000';
    infoPanel.style.boxShadow = '0 0 15px var(--cyber-cyan)';
    infoPanel.style.transition = 'all 0.3s ease';
    document.getElementById('skills-matrix').appendChild(infoPanel);
  }
  
  infoPanel.innerHTML = `
    <h3 style="margin: 0 0 10px 0; color: var(--cyber-cyan); text-shadow: 0 0 10px var(--cyber-cyan);">${data.name}</h3>
    <p style="margin: 5px 0;">Category: ${data.category}</p>
  `;
  
  infoPanel.style.opacity = '1';
}

function hideNodeInfo() {
  const infoPanel = document.getElementById('skill-info-panel');
  if (infoPanel) {
    infoPanel.style.opacity = '0';
  }
}

// Helper function to get color based on skill category
function getColorForCategory(category, colors) {
  const categoryColors = {
    language: colors.cyberCyan,   // Cyan
    framework: colors.neonPink,   // Pink
    frontend: colors.neonGreen,   // Green
    backend: colors.neonPurple,   // Purple
    database: colors.neonOrange,  // Orange
    devops: colors.neonBlue,      // Blue
    cloud: colors.neonYellow      // Yellow
  };
  
  return categoryColors[category] || colors.cyberCyan;
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', initSkillsGraph);
