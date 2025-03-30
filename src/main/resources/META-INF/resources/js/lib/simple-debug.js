// Simple debug helper for Three.js applications
console.log("Debug helper loaded");

// Override console.error to make it more visible
const originalError = console.error;
console.error = function() {
    // Call the original
    originalError.apply(console, arguments);
    
    // Make it more visible in the UI
    try {
        const debugDiv = document.getElementById('debug-div') || createDebugDiv();
        const errorMessage = Array.from(arguments).join(' ');
        
        const errorLine = document.createElement('div');
        errorLine.style.color = 'red';
        errorLine.style.fontWeight = 'bold';
        errorLine.textContent = `ERROR: ${errorMessage}`;
        
        debugDiv.appendChild(errorLine);
        debugDiv.scrollTop = debugDiv.scrollHeight;
    } catch (e) {
        // Don't let debug errors cause more problems
    }
};

// Helper to log information to a visible div
function log(message, type = 'info') {
    console.log(message);
    
    try {
        const debugDiv = document.getElementById('debug-div') || createDebugDiv();
        
        const logLine = document.createElement('div');
        switch(type) {
            case 'error':
                logLine.style.color = 'red';
                break;
            case 'warning':
                logLine.style.color = 'orange';
                break;
            case 'success':
                logLine.style.color = 'green';
                break;
            default:
                logLine.style.color = 'white';
        }
        
        logLine.textContent = message;
        debugDiv.appendChild(logLine);
        
        // Auto-scroll to bottom
        debugDiv.scrollTop = debugDiv.scrollHeight;
    } catch (e) {
        // Don't let debug errors cause more problems
    }
}

// Create a div to show debug info
function createDebugDiv() {
    const debugDiv = document.createElement('div');
    debugDiv.id = 'debug-div';
    debugDiv.style.position = 'fixed';
    debugDiv.style.bottom = '10px';
    debugDiv.style.right = '10px';
    debugDiv.style.width = '400px';
    debugDiv.style.height = '200px';
    debugDiv.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    debugDiv.style.color = 'white';
    debugDiv.style.fontFamily = 'monospace';
    debugDiv.style.fontSize = '12px';
    debugDiv.style.padding = '10px';
    debugDiv.style.borderRadius = '5px';
    debugDiv.style.zIndex = '9999';
    debugDiv.style.overflow = 'auto';
    debugDiv.style.border = '1px solid #444';
    
    // Collapsible header
    const header = document.createElement('div');
    header.textContent = 'Debug Info (click to hide)';
    header.style.cursor = 'pointer';
    header.style.fontWeight = 'bold';
    header.style.borderBottom = '1px solid #444';
    header.style.paddingBottom = '5px';
    header.style.marginBottom = '5px';
    
    header.addEventListener('click', () => {
        const content = debugDiv.querySelector('#debug-content');
        content.style.display = content.style.display === 'none' ? 'block' : 'none';
    });
    
    debugDiv.appendChild(header);
    
    // Content container
    const content = document.createElement('div');
    content.id = 'debug-content';
    debugDiv.appendChild(content);
    
    document.body.appendChild(debugDiv);
    return content;
}

// Helper to check if THREE.js is loaded properly
window.addEventListener('DOMContentLoaded', function() {
    try {
        if (typeof THREE === 'undefined') {
            console.error('THREE is not defined - Three.js is not loaded properly');
        } else {
            log('THREE.js loaded successfully', 'success');
            log(`THREE.js version: ${THREE.REVISION}`, 'info');
            
            // Check for essential objects
            if (typeof THREE.Scene !== 'function') {
                console.error('THREE.Scene is not defined');
            }
            if (typeof THREE.WebGLRenderer !== 'function') {
                console.error('THREE.WebGLRenderer is not defined');
            }
            if (typeof THREE.PerspectiveCamera !== 'function') {
                console.error('THREE.PerspectiveCamera is not defined');
            }
            
            // Check for OrbitControls
            if (typeof THREE.OrbitControls !== 'function') {
                console.error('THREE.OrbitControls is not defined - OrbitControls extension is missing');
            } else {
                log('OrbitControls loaded successfully', 'success');
            }
        }
    } catch (e) {
        console.error('Error checking THREE.js:', e);
    }
});
