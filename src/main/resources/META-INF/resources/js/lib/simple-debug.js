// Simple debug utilities
window.debug = {
  log: function(message) {
    console.log('[DEBUG] ' + message);
  },
  
  showDebugPanel: function() {
    let panel = document.getElementById('debug-panel');
    if (!panel) {
      panel = document.createElement('div');
      panel.id = 'debug-panel';
      panel.style.position = 'fixed';
      panel.style.top = '10px';
      panel.style.left = '10px';
      panel.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
      panel.style.color = '#00ffff';
      panel.style.padding = '10px';
      panel.style.borderRadius = '5px';
      panel.style.zIndex = '10000';
      panel.style.maxWidth = '400px';
      panel.style.maxHeight = '200px';
      panel.style.overflow = 'auto';
      panel.style.fontFamily = 'monospace';
      document.body.appendChild(panel);
    }
    return panel;
  },
  
  output: function(message) {
    const panel = this.showDebugPanel();
    panel.innerHTML += message + '<br>';
  },
  
  clear: function() {
    const panel = document.getElementById('debug-panel');
    if (panel) {
      panel.innerHTML = '';
    }
  }
};
