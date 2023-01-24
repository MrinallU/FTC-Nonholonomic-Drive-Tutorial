    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, 
         and passing it to the Vuforia engine.
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
        .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext
        .getPackageName());
        
        VuforiaLocalizer.Parameters parameters = 
        new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        vuforia.setFrameQueueCapacity(1);

        vuforia.enableConvertFrameToBitmap();
        
        // Take image
        VuforiaLocalizer.CloseableFrame closeableFrame = vuforia.getFrameQueue().take();
        // Vuforia engine returns RGB grid
        bm = vuforia.convertFrameToBitmap(closeableFrame); 
    }

    
    public void drawRectangle(Bitmap bm, int left, int top, int right, int bottom){
        // Iterate through every point inside the rectangle.
        for(int x = left; x <= right; x++){ 
            for(int y = top; y <= bottom; y++){
                if(x == left || x == right || y == top || y == bottom){
                    // For each pixel on the rectangle mark it
                    bm.setPixel(x, y, Color.BLACK); 
                }
            }
        }
    }
