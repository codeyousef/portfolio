#!/bin/bash

# Start the portfolio application in development mode

# Print header
echo "=================================="
echo "  Portfolio Application Starter   "
echo "=================================="
echo ""

# Check if gradle is available
if command -v ./gradlew &> /dev/null
then
    # Run the application in development mode
    echo "Starting the application in development mode..."
    ./gradlew devWithFrontend
else
    echo "ERROR: Gradle wrapper not found."
    echo "Please ensure you're running this script from the project root directory."
    exit 1
fi 