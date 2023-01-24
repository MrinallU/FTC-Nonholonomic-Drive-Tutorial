    public double[] calculateAverageRGB(Bitmap bm, int left, int top, 
    int right, int bottom){
        long numTotalPixels = 0;
        double[] colorValues = {0, 0, 0, 0};
        for(int x = left; x <= right; x++){ // iterate through the rectangle
            for(int y = top; y <= bottom; y++){
                // SUm the respective values
                int pixel = bm.getPixel(x, y);
                colorValues[0] += Color.alpha(pixel);  // RED
                colorValues[1] += Color.red(pixel); // GREEN
                colorValues[2] += Color.green(pixel); //BLUE
                colorValues[3] += Color.blue(pixel);
                numTotalPixels++;
            }
        }

        for(int i = 0; i < colorValues.length; i++){
            colorValues[i] = colorValues[i] / numTotalPixels; // Computer average
        }

        return colorValues;
    }
    
    public int readBarcode(String auto) throws InterruptedException{
        int position = 0;
        Bitmap bm;
        double[] posOne = new double[4];
        double[] posTwo = new double[4];
        double[] posThree = new double[4];
        
        // take the image
        VuforiaLocalizer.CloseableFrame closeableFrame = vuforia.
        getFrameQueue().take();
        bm = vuforia.convertFrameToBitmap(closeableFrame);
        
        
        // Calculate the average RGB pixel values for each square on the barcode.
        if(auto == "redPrimary"){
            posOne = calculateAverageRGB(bm, 976, 31, 1087, 80);
            posTwo = calculateAverageRGB(bm, 439, 81, 558, 151);
            posThree = calculateAverageRGB(bm, 0, 0, 0, 0);
        }
        
        // Return highest average
        if(posOne[3] < blueThreshold){
            return 0;
        }else if(posTwo[3] < blueThreshold){
            return 1;
            }else{
            return 2;
        }
    }
    