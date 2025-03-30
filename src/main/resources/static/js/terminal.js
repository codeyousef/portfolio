// Terminal effect for the portfolio
document.addEventListener('DOMContentLoaded', function() {
    const terminalContainer = document.getElementById('terminal-container');
    if (!terminalContainer) return;

    // Create terminal elements
    const terminal = document.createElement('div');
    terminal.className = 'terminal';
    
    const terminalHeader = document.createElement('div');
    terminalHeader.className = 'terminal-header';
    terminalHeader.innerHTML = `
        <div class="terminal-title">Portfolio Terminal</div>
        <div class="terminal-controls">
            <span class="terminal-control close"></span>
            <span class="terminal-control minimize"></span>
            <span class="terminal-control maximize"></span>
        </div>
    `;
    
    const terminalContent = document.createElement('div');
    terminalContent.className = 'terminal-content';
    
    const terminalInput = document.createElement('div');
    terminalInput.className = 'terminal-input-line';
    terminalInput.innerHTML = `
        <span class="prompt">$</span>
        <input type="text" class="terminal-input" autofocus>
    `;

    // Add elements to terminal
    terminal.appendChild(terminalHeader);
    terminal.appendChild(terminalContent);
    terminal.appendChild(terminalInput);

    // Clear the container and add the terminal
    terminalContainer.innerHTML = '';
    terminalContainer.appendChild(terminal);

    // Add some initial content
    const welcomeMessage = document.createElement('div');
    welcomeMessage.className = 'terminal-line';
    welcomeMessage.innerHTML = `
        <span class="prompt">$</span>
        <span class="command">welcome</span>
        <div class="output">
            Welcome to my portfolio terminal!<br>
            Type 'help' to see available commands.
        </div>
    `;
    terminalContent.appendChild(welcomeMessage);

    // Handle input
    const input = terminalInput.querySelector('.terminal-input');
    input.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            const command = input.value.trim();
            if (command) {
                // Add command to terminal
                const commandLine = document.createElement('div');
                commandLine.className = 'terminal-line';
                commandLine.innerHTML = `
                    <span class="prompt">$</span>
                    <span class="command">${command}</span>
                `;
                terminalContent.appendChild(commandLine);

                // Process command
                processCommand(command, terminalContent);

                // Clear input
                input.value = '';
            }
        }
    });

    // Command processing
    function processCommand(command, terminalContent) {
        const output = document.createElement('div');
        output.className = 'output';

        switch (command.toLowerCase()) {
            case 'help':
                output.innerHTML = `
                    Available commands:<br>
                    - help: Show this help message<br>
                    - about: Learn about me<br>
                    - projects: View my projects<br>
                    - skills: See my skills<br>
                    - contact: Get contact information<br>
                    - clear: Clear the terminal
                `;
                break;
            case 'about':
                output.innerHTML = `
                    I'm a software developer passionate about creating innovative solutions.<br>
                    My expertise includes Kotlin, Quarkus, and modern web technologies.
                `;
                break;
            case 'projects':
                output.innerHTML = `
                    Loading projects...<br>
                    Check out my featured projects in the Projects section below!
                `;
                break;
            case 'skills':
                output.innerHTML = `
                    My skills include:<br>
                    - Backend Development (Kotlin, Quarkus)<br>
                    - Frontend Development (HTML, CSS, JavaScript)<br>
                    - Database Management<br>
                    - Cloud Technologies<br>
                    - DevOps Practices
                `;
                break;
            case 'contact':
                output.innerHTML = `
                    You can reach me at:<br>
                    - Email: contact@example.com<br>
                    - GitHub: github.com/yourusername<br>
                    - LinkedIn: linkedin.com/in/yourusername
                `;
                break;
            case 'clear':
                terminalContent.innerHTML = '';
                return;
            default:
                output.innerHTML = `Command not found: ${command}. Type 'help' for available commands.`;
        }

        terminalContent.appendChild(output);
    }
}); 