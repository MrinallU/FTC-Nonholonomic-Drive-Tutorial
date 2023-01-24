
public class T3_T265Odometry {
    // Variables
    private double xPos, yPos, angle;
    private HardwareMap hardwareMap;
    private double cX = 0, cY = 0, cAng = 0;
    private double xAdj = 0, yAdj = 0, angAdj = 0;
    private boolean positionAdjusted = false;
    private T265Camera.CameraUpdate update;
    private static T265Camera slamara;

    // Constructor
    public T3_T265Odometry(double xPos, double yPos, double angle, 
    HardwareMap hardwareMap) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.hardwareMap = hardwareMap;
    }
    
    // Adjust camera to simulate robot center
    public void setAdjustments(){
        xAdj = -cX + xPos;
        yAdj = -cY + yPos;
        angAdj = -cAng + angle;
    }
    
    // Initialize Camera and Check for connection in a previous opMode    
    public void initializeT265(){
        String logMessage = "Failure";
        int attempts = Integer.MAX_VALUE;
        while(attempts > 0) {
            if (slamara == null) {
                slamara = new T265Camera(new Transform2d(), 1, 
                hardwareMap.appContext);
            }
            
            try {
                slamara.start();
            } catch (Exception e) { // Check for previous connection
                if (e.getMessage().toString().equals("Camera is already started")) {
                    logMessage = "Success!";
                    break;
                } else {
                    throw new RuntimeException(e.getMessage());
                }
            }

            --attempts;
        }
        // Set start position
        Pose2d startPos = new Pose2d(new Translation2d(xPos * 0.0254
        , yPos * 0.0254), 
        new Rotation2d(0));
        
        slamara.setPose(startPos);
    }
    
    public void updatePosition() {
        // Receive API update 
        update = slamara.getLastReceivedCameraUpdate();

        if(update != null){
            setCurrents(); // set variables

            if(!positionAdjusted){
                positionAdjusted = true;
                setAdjustments(); // initial robot offset
            }
            
            // Get displacement for movement
            xPos = cX + xAdj; 
            yPos = cY + yAdj;
            angle = normalizeAngle(cAng + angAdj);

            // Set string so values can be passed to telemetry
            outStr = "xPos: " + format(xPos) + 
            "\nyPos: " + format(yPos) + "\nAngle: " + format(angle);
        }
    }

    private void setCurrents(){
        // Update variables
        Translation2d translation = new Translation2d(
        update.pose.getTranslation().getX() / 0.0254, 
        update.pose.getTranslation().getY() / 0.0254);
        cX = translation.getX();
        cY = translation.getY();
        cAng = update.pose.getRotation().getDegrees();
    }

    public void startT265(){
        slamara.start();
    }

    public void stopT265(){
        slamara.stop();
        slamara.free();
    }

    public double normalizeAngle(double rawAngle) {
        double scaledAngle = rawAngle % 360;
        if ( scaledAngle < 0 ) {
            scaledAngle += 360;
        }

        if ( scaledAngle > 180 ) {
            scaledAngle -= 360;
        }

        return scaledAngle;
    }

    public String displayPositions() {
        return outStr;
    }
    
    private String format(double num){
        return String.format("%.3f", num);
    }
}
