#!/bin/bash

# Start the portfolio application in hot reload mode

# Print header
echo "=================================="
echo "  Portfolio Hot Reload Starter    "
echo "=================================="
echo ""

# Show information
echo "This script will display instructions for running the application in hot reload mode."
echo "You'll need to open two terminal windows to run both servers."
echo ""
echo "Instructions:"
echo "1. In the first terminal, run: ./gradlew :backend:quarkusDevHotApi"
echo "2. In the second terminal, run: ./gradlew :backend:vueDevServer"
echo ""
echo "Then access:"
echo "- Frontend (Vue.js): http://localhost:5173"
echo "- Backend API: http://localhost:8080"
echo ""
echo "The Vue dev server will proxy API requests to the backend automatically."
echo ""

# Ask user if they want to launch the API server now
read -p "Would you like to start the API server now? (y/n) " choice
case "$choice" in 
  y|Y ) 
    echo "Starting API server..."
    echo "You'll need to open another terminal to run the Vue.js dev server."
    ./gradlew :backend:quarkusDevHotApi
    ;;
  * ) 
    echo "Exiting. Run the commands manually when you're ready."
    ;;
esac 