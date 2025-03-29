// Main Three.js setup
let camera, scene, renderer;
let geometry, material, mesh;
let mouseX = 0, mouseY = 0;
let windowHalfX = window.innerWidth / 2;
let windowHalfY = window.innerHeight / 2;

// Grid parameters
const gridSize = 40;
const gridDivisions = 20;
const gridColor = 0x00f7ff;

function init() {
    // Canvas setup
    const canvas = document.getElementById('bg-canvas');

    // Scene setup
    scene = new THREE.Scene();
    scene.fog = new THREE.FogExp2(0x020024, 0.05);

    // Camera setup
    camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    camera.position.z = 15;

    // Create grid
    const gridHelper = new THREE.GridHelper(gridSize, gridDivisions, gridColor, gridColor);
    gridHelper.position.y = -5;
    scene.add(gridHelper);

    // Create parametric background
    const parametricGeom = new THREE.PlaneGeometry(100, 100, 20, 20);
    const parametricMat = new THREE.MeshBasicMaterial({
        color: 0x090979,
        wireframe: true
    });
    mesh = new THREE.Mesh(parametricGeom, parametricMat);
    mesh.rotation.x = -Math.PI / 2;
    mesh.position.y = -5;
    scene.add(mesh);

    // Add star particles
    addStars();

    // Renderer setup
    renderer = new THREE.WebGLRenderer({
        canvas: canvas,
        antialias: true,
        alpha: true
    });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    // Set clear color with transparency
    renderer.setClearColor(0x000000, 0);

    // Mouse move event
    document.addEventListener('mousemove', onDocumentMouseMove);

    // Device orientation for mobile
    if (window.DeviceOrientationEvent) {
        window.addEventListener('deviceorientation', onDeviceOrientation);
    }

    // Handle window resize
    window.addEventListener('resize', onWindowResize);

    // Initialize project card 3D models
    initProjectModels();
}

function addStars() {
    const starsGeometry = new THREE.BufferGeometry();
    const starsMaterial = new THREE.PointsMaterial({
        color: 0xffffff,
        size: 0.1
    });

    const starsVertices = [];
    for (let i = 0; i < 1000; i++) {
        const x = (Math.random() - 0.5) * 100;
        const y = (Math.random() - 0.5) * 100;
        const z = (Math.random() - 0.5) * 100;
        starsVertices.push(x, y, z);
    }

    starsGeometry.setAttribute('position', new THREE.Float32BufferAttribute(starsVertices, 3));
    const stars = new THREE.Points(starsGeometry, starsMaterial);
    scene.add(stars);
}

function initProjectModels() {
    // Find all model containers
    const modelContainers = document.querySelectorAll('.model-container');

    modelContainers.forEach(container => {
        // Create a separate scene for each model
        const modelScene = new THREE.Scene();

        // Create a camera for this model
        const modelCamera = new THREE.PerspectiveCamera(50, container.clientWidth / container.clientHeight, 0.1, 1000);
        modelCamera.position.z = 5;

        // Create a renderer
        const modelRenderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
        modelRenderer.setSize(container.clientWidth, container.clientHeight);
        container.appendChild(modelRenderer.domElement);

        // Add a basic cube as placeholder
        // In production, you would load the actual model from container.dataset.modelUrl
        const geometry = new THREE.BoxGeometry(2, 2, 2);
        const material = new THREE.MeshBasicMaterial({
            color: 0x00f7ff,
            wireframe: true
        });
        const cube = new THREE.Mesh(geometry, material);
        modelScene.add(cube);

        // Animation function
        function animateModel() {
            requestAnimationFrame(animateModel);
            cube.rotation.x += 0.01;
            cube.rotation.y += 0.01;
            modelRenderer.render(modelScene, modelCamera);
        }

        animateModel();
    });
}

function onDocumentMouseMove(event) {
    mouseX = (event.clientX - windowHalfX) / 100;
    mouseY = (event.clientY - windowHalfY) / 100;
}

function onDeviceOrientation(event) {
    if (event.beta && event.gamma) {
        // Limit rotation to 7 degrees as specified in design doc
        const maxRotation = 7;
        const betaRotation = Math.min(Math.max(event.beta - 90, -maxRotation), maxRotation) / 180 * Math.PI;
        const gammaRotation = Math.min(Math.max(event.gamma, -maxRotation), maxRotation) / 180 * Math.PI;

        camera.position.y = betaRotation * 2;
        camera.position.x = gammaRotation * 2;
        camera.lookAt(scene.position);
    }
}

function onWindowResize() {
    windowHalfX = window.innerWidth / 2;
    windowHalfY = window.innerHeight / 2;

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize(window.innerWidth, window.innerHeight);
}

function animate() {
    requestAnimationFrame(animate);

    // Smooth lerping for camera movement
    camera.position.x += (mouseX - camera.position.x) * 0.05;
    camera.position.y += (-mouseY - camera.position.y) * 0.05;
    camera.lookAt(scene.position);

    // Animate mesh
    if (mesh) {
        const positions = mesh.geometry.attributes.position.array;
        const time = Date.now() * 0.0005;

        for (let i = 0; i < positions.length; i += 3) {
            positions[i + 2] = Math.sin((i + time) * 0.005) * 0.3;
        }

        mesh.geometry.attributes.position.needsUpdate = true;
    }

    renderer.render(scene, camera);
}

document.addEventListener('DOMContentLoaded', () => {
    init();
    animate();
});