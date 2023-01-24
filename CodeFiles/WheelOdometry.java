public class WheelOdometry {
    private final double WHEEL_RADIUS = 2; // WHEEL_RADIUS
    private final double TICKS_PER_REV = 538; // Manually Calibrated
    private double x, y, theta;
    private double prevL = 0, prevR = 0;
    private String outStr;
    // Construtor
    public WheelOdometry(int x, int y, int theta){
            this.x = x;
            this.y  = y;
            this.theta = theta;
    }
    
    // Apply the iterative process
    public void updatePosition(double currLTick, double currRTick, double currTheta){
        double dL =  (2*Math.PI*WHEEL_RADIUS) * (
                        (currLTick - prevL) / TICKS_PER_REV
                );
        double dR =  (2*Math.PI*WHEEL_RADIUS) * (
                (currRTick - prevR) / TICKS_PER_REV
        );
        double dC = (dL + dR) / 2;

        double dX = dC * Math.cos(theta);
        double dY = dC * Math.sin(theta);
        x += dX; y += dY; theta = Math.toRadians(currTheta);
        outStr = "xPos: " + format(x) + "\nyPos: " + format(y) + 
        "\nAngle: " + format(theta);

        prevL = currLTick;
        prevR = currRTick;
    }

    public void setPose(int x, int y, int theta){
        this.x = x;
        this.y = y ;
        this.theta = theta;
    }
    
    // Output positions to telemetry
    public String displayPositions(){
        return outStr;
    }

    private String format(double num){
        return String.format("%.3f", num);
    }
}
