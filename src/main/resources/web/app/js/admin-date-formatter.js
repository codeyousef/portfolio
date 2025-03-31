// Format dates to be human-readable
document.addEventListener('DOMContentLoaded', function() {
  // Format all dates in the table
  const dateCells = document.querySelectorAll('table.table td:nth-child(3)');

  dateCells.forEach(cell => {
    if (cell.textContent.includes('T') || cell.textContent.includes('-')) {
      try {
        // Parse the date string
        const date = new Date(cell.textContent.trim());

        // Format as a human-readable date
        if (!isNaN(date.getTime())) {
          const options = {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
          };

          cell.textContent = date.toLocaleDateString('en-US', options);
        }
      } catch (e) {
        console.error('Error formatting date:', e);
      }
    }
  });

  // Extra target for other date displays that might exist
  const dateTexts = document.querySelectorAll('[data-date]');
  dateTexts.forEach(element => {
    try {
      const date = new Date(element.getAttribute('data-date'));
      if (!isNaN(date.getTime())) {
        const options = {
          year: 'numeric',
          month: 'short',
          day: 'numeric'
        };

        element.textContent = date.toLocaleDateString('en-US', options);
      }
    } catch (e) {
      console.error('Error formatting date attribute:', e);
    }
  });
});