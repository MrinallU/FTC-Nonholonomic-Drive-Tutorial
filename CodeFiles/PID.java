    public boolean yTo(double targetY, double timeout, double powerCap, 
    double minDifference, LinearOpMode opMode, boolean negate){
        
        ElapsedTime time = new ElapsedTime();
        double prevTime = 0; 
        while(Math.abs(Y - targetY) > minDifference && time.milliseconds() < timeout
        && opMode.opModeIsActive()){
            resetCache();
        
            // Update visual and wheel odometry
            wheelOdometry.updatePosition(
                    leftDrive.encoderReading(),
                    rightDrive.encoderReading(),
                    getAngle());
            t265.updatePosition(); 
            
            // Calculate error in position
            yDiff = targetYPos - t265.getY();

            // Updating pid components
            pY = k_p_s * yDiff; // P
            dY =  k_d_s * (yDiff - prevYDiff) / (currTime - prevTime); //D

            // Sum the P and D components for the drive powers
            double drive = Range.clip(pY + dY, -powerCap, powerCap) * -1; 

            if(negate){
                drive *= -1;
            }

            // Output the calculated values to the motor drives.
            leftDrive.setPower(drive);
            rightDrive.setPower(drive);
            backleftDrive.setPower(drive);
            backrightDrive.setPower(drive);
            
             // prepare for next iteration
            prevTime = currTime;
            prevXDiff = xDiff;
            prevYDiff = yDiff;
        }
        stopBot();
    }