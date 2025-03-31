// Ultra simplified Text Geometry for Three.js
// This is a bare-bones version just to get things working

THREE.TextGeometry = function(text, parameters) {
    // Just return a simple box with the text as a property
    const geometry = new THREE.BoxGeometry(text.length * 2, 2, 0.5);
    geometry.text = text;
    
    // Store a reference to the parameters for debugging
    geometry.textParams = parameters;
    
    return geometry;
};

