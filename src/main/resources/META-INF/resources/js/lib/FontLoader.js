// Ultra simplified FontLoader for Three.js
// Just a stub to make things work

THREE.FontLoader = function() {
    this.load = function(url, onLoad) {
        // Create a mock font object
        const mockFont = {
            isFont: true,
            data: {
                resolution: 1,
                boundingBox: { yMax: 1, yMin: 0 }
            },
            generateShapes: function() {
                return [];
            }
        };
        
        // Call the callback with our mock font
        if (onLoad) {
            setTimeout(function() {
                onLoad(mockFont);
            }, 10);
        }
        
        return this;
    };
};

THREE.Font = function() {
    this.isFont = true;
};

