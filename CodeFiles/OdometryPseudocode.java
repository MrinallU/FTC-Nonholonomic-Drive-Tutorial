void updatePosition(){
    Point currentPosition = (0, 0, 0); // initialized to origin 
    // point, equal to $O$ matrix; changes
    // depending on the starting point of the robot on field
    Input OdometryInputSource = new Input();
    
        /* By continuously summing the amount the robot has moved from the previously 
        calculated position, the robot can be localized.
        Theoretical Equation: currentPosition = origin + overallDisplacement
        
        New Equation: currentPosition = 
        previouslyCalculatedPosition + displacementFromPreviousPosition
        */
    while(matchIsOccurring){
        Point displacement = OdometryInputSource.getDisplacement(); // receives
        // the $\Delta$ matrix of the equation
        

        currentPosition.x = currentPosition.x + displacement.x;
        currentPosition.y = currentPosition.y + displacement.y;
        currentPosition.theta = currentPosition.theta + displacement.theta;
    }
}