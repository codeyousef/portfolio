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

// Create a simple fallback skills view
function createSkillsMatrix() {
  console.log('Creating fallback skills matrix');
  
  // Get the skills matrix container
  const container = document.querySelector('.skills-matrix');
  if (!container) {
    console.error('Skills matrix container not found');
    return;
  }
  
  // Create skills section if it doesn't exist
  let skillsSection = document.getElementById('skills');
  if (!skillsSection) {
    skillsSection = document.createElement('section');
    skillsSection.id = 'skills';
    skillsSection.className = 'skills-section';
    const title = document.createElement('h2');
    title.className = 'glowing-text';
    title.textContent = 'Skills';
    
    const description = document.createElement('p');
    description.textContent = 'These are my skills in various technologies and categories.';
    
    skillsSection.appendChild(title);
    skillsSection.appendChild(description);
    
    // Find a good place to add it - after the projects section
    const projectsSection = document.querySelector('.projects-section');
    if (projectsSection) {
      projectsSection.parentNode.insertBefore(skillsSection, projectsSection.nextSibling);
    } else {
      // If no projects section, add it to main
      const main = document.querySelector('main');
      if (main) {
        main.appendChild(skillsSection);
      }
    }
    
    // Add skills matrix container
    const matrixContainer = document.createElement('div');
    matrixContainer.id = 'skills-matrix';
    matrixContainer.className = 'skills-matrix';
    skillsSection.appendChild(matrixContainer);
    
    // Update container reference
    container = matrixContainer;
  } else {
    // Clear existing content
    container.innerHTML = '';
  }
  
  // Group skills by category
  const categories = {};
  skills.forEach(skill => {
    if (!categories[skill.category]) {
      categories[skill.category] = [];
    }
    categories[skill.category].push(skill);
  });
  
  // Create a grid layout for categories
  const categoriesContainer = document.createElement('div');
  categoriesContainer.style.display = 'grid';
  categoriesContainer.style.gridTemplateColumns = 'repeat(auto-fill, minmax(300px, 1fr))';
  categoriesContainer.style.gap = '30px';
  categoriesContainer.style.margin = '30px 0';
  
  // Create a card for each category
  for (const [category, categorySkills] of Object.entries(categories)) {
    const categoryCard = document.createElement('div');
    categoryCard.className = 'glass-morphic';
    categoryCard.style.padding = '20px';
    
    // Category title
    const categoryTitle = document.createElement('h3');
    categoryTitle.className = 'glowing-text';
    categoryTitle.textContent = category.charAt(0).toUpperCase() + category.slice(1);
    categoryTitle.style.marginBottom = '20px';
    categoryCard.appendChild(categoryTitle);
    
    // Add each skill in this category
    categorySkills.forEach(skill => {
      const skillContainer = document.createElement('div');
      skillContainer.style.marginBottom = '15px';
      
      // Skill name and level
      const nameRow = document.createElement('div');
      nameRow.style.display = 'flex';
      nameRow.style.justifyContent = 'space-between';
      nameRow.style.marginBottom = '5px';
      
      const skillName = document.createElement('div');
      skillName.textContent = skill.name;
      skillName.style.fontWeight = 'bold';
      
      const skillLevel = document.createElement('div');
      skillLevel.textContent = `${Math.round(skill.level * 100)}%`;
      
      nameRow.appendChild(skillName);
      nameRow.appendChild(skillLevel);
      skillContainer.appendChild(nameRow);
      
      // Skill progress bar
      const progressContainer = document.createElement('div');
      progressContainer.style.width = '100%';
      progressContainer.style.height = '8px';
      progressContainer.style.backgroundColor = 'rgba(0,0,0,0.2)';
      progressContainer.style.borderRadius = '4px';
      progressContainer.style.overflow = 'hidden';
      
      const progressBar = document.createElement('div');
      progressBar.style.height = '100%';
      progressBar.style.width = `${skill.level * 100}%`;
      progressBar.style.backgroundColor = 'var(--cyber-cyan)';
      progressBar.style.boxShadow = '0 0 8px var(--cyber-cyan)';
      
      progressContainer.appendChild(progressBar);
      skillContainer.appendChild(progressContainer);
      
      categoryCard.appendChild(skillContainer);
    });
    
    categoriesContainer.appendChild(categoryCard);
  }
  
  container.appendChild(categoriesContainer);
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
  // Check if skills section exists
  console.log('Checking for skills matrix');
  
  createSkillsMatrix();
  
  // In case the 3D doesn't render properly
  setTimeout(function() {
    const container = document.getElementById('skills-matrix');
    if (container && container.children.length === 0) {
      console.log('Skills matrix empty after timeout, creating fallback');
      createSkillsMatrix();
    }
  }, 2000);
});
