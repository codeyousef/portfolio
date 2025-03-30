// Project models initialization
document.addEventListener('DOMContentLoaded', function() {
    initProjectModels();
});

// Retry a few times to make sure models are initialized
let projectModelAttempts = 0;
const maxProjectModelAttempts = 5;

function initProjectModels() {
    // Find all model containers
    const modelContainers = document.querySelectorAll('.model-container');
    console.log(`Found ${modelContainers.length} model containers`);
    
    if (modelContainers.length === 0) {
        // Try again if we haven't hit the max attempts
        if (projectModelAttempts < maxProjectModelAttempts) {
            projectModelAttempts++;
            setTimeout(initProjectModels, 1000);
            return;
        }
    }
    
    modelContainers.forEach((container, index) => {
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
        
        // Define colors
        const colors = {
            dark: {
                cyberCyan: 0x00ffff,
                neonPink: 0xff00ff,
                neonPurple: 0x9900ff,
                neonGreen: 0x00ff00
            },
            light: {
                cyberCyan: 0x00bbff,
                neonPink: 0xff0066,
                neonPurple: 0x8800cc,
                neonGreen: 0x00cc00
            }
        };
        
        // Check theme
        const themeIsDark = !document.body.hasAttribute('data-theme') || 
                           document.body.getAttribute('data-theme') !== 'light';
        
        // Create material with the liquid metal look
        const mainColor = themeIsDark ? 
            [colors.dark.cyberCyan, colors.dark.neonPink, colors.dark.neonPurple, colors.dark.neonGreen][index % 4] : 
            [colors.light.cyberCyan, colors.light.neonPink, colors.light.neonPurple, colors.light.neonGreen][index % 4];
        
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
    });
}
