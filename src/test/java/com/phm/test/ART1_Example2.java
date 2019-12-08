
package com.phm.test;

/**
 *
 * @author phm
 */

public class ART1_Example2
{
    // If you look closely, you can see the letters made out of 1's
    private static int A1[] = new int[] {0, 0, 1, 1, 0, 0, 0,                                          
                                         0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 1, 0, 1, 0, 0, 
                                         0, 0, 1, 0, 1, 0, 0, 
                                         0, 1, 1, 1, 1, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         1, 1, 1, 0, 1, 1, 1};

    private static int B1[] = new int[] {1, 1, 1, 1, 1, 1, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 1, 1, 1, 1, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         1, 1, 1, 1, 1, 1, 0};

    private static int C1[] = new int[] {0, 0, 1, 1, 1, 1, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 0, 1, 1, 1, 1, 0};

    private static int D1[] = new int[] {1, 1, 1, 1, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         1, 1, 1, 1, 1, 0, 0};

    private static int E1[] = new int[] {1, 1, 1, 1, 1, 1, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 0, 
                                         0, 1, 0, 1, 0, 0, 0, 
                                         0, 1, 1, 1, 0, 0, 0, 
                                         0, 1, 0, 1, 0, 0, 0, 
                                         0, 1, 0, 0, 0, 0, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         1, 1, 1, 1, 1, 1, 1};

    private static int J1[] = new int[] {0, 0, 0, 1, 1, 1, 1, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 0, 1, 1, 1, 0, 0};

    private static int K1[] = new int[] {1, 1, 1, 0, 0, 1, 1, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 0, 1, 0, 0, 0, 
                                         0, 1, 1, 0, 0, 0, 0, 
                                         0, 1, 1, 0, 0, 0, 0, 
                                         0, 1, 0, 1, 0, 0, 0, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         1, 1, 1, 0, 0, 1, 1};

    private static int A2[] = new int[] {0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 1, 0, 1, 0, 0, 
                                         0, 0, 1, 0, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 1, 1, 1, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0};

    private static int B2[] = new int[] {1, 1, 1, 1, 1, 1, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 1, 1, 1, 1, 1, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 1, 1, 1, 1, 1, 0};

    private static int C2[] = new int[] {0, 0, 1, 1, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 0, 1, 1, 1, 0, 0};

    private static int D2[] = new int[] {1, 1, 1, 1, 1, 0, 0, 
                                         1, 0, 0, 0, 0, 1, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 1, 0, 
                                         1, 1, 1, 1, 1, 0, 0};

    private static int E2[] = new int[] {1, 1, 1, 1, 1, 1, 1, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 1, 1, 1, 1, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 1, 1, 1, 1, 1, 1};

    private static int J2[] = new int[] {0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 0, 1, 1, 1, 0, 0};

    private static int K2[] = new int[] {1, 0, 0, 0, 0, 1, 0, 
                                         1, 0, 0, 0, 1, 0, 0, 
                                         1, 0, 0, 1, 0, 0, 0, 
                                         1, 0, 1, 0, 0, 0, 0, 
                                         1, 1, 0, 0, 0, 0, 0, 
                                         1, 0, 1, 0, 0, 0, 0, 
                                         1, 0, 0, 1, 0, 0, 0, 
                                         1, 0, 0, 0, 1, 0, 0, 
                                         1, 0, 0, 0, 0, 1, 0};

    private static int A3[] = new int[] {0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 0, 1, 0, 0, 0, 
                                         0, 0, 1, 0, 1, 0, 0, 
                                         0, 0, 1, 0, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 1, 1, 1, 1, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 1, 0, 0, 0, 1, 1};

    private static int B3[] = new int[] {1, 1, 1, 1, 1, 1, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 1, 1, 1, 1, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         1, 1, 1, 1, 1, 1, 0};

    private static int C3[] = new int[] {0, 0, 1, 1, 1, 0, 1, 
                                         0, 1, 0, 0, 0, 1, 1, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 0, 
                                         1, 0, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 0, 1, 1, 1, 0, 0};

    private static int D3[] = new int[] {1, 1, 1, 1, 0, 0, 0, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         1, 1, 1, 1, 0, 0, 0};

    private static int E3[] = new int[] {1, 1, 1, 1, 1, 1, 1, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 1, 1, 1, 0, 0, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 0, 0, 
                                         0, 1, 0, 0, 0, 0, 0, 
                                         0, 1, 0, 0, 0, 0, 1, 
                                         1, 1, 1, 1, 1, 1, 1};

    private static int J3[] = new int[] {0, 0, 0, 0, 1, 1, 1, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 0, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 0, 1, 1, 1, 0, 0};

    private static int K3[] = new int[] {1, 1, 1, 0, 0, 1, 1, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 0, 1, 0, 0, 0, 
                                         0, 1, 1, 0, 0, 0, 0, 
                                         0, 1, 0, 1, 0, 0, 0, 
                                         0, 1, 0, 0, 1, 0, 0, 
                                         0, 1, 0, 0, 0, 1, 0, 
                                         1, 1, 1, 0, 0, 1, 1};

    private static final int N = 63;    //Number of components in an input vector.
    private static final int M = 12;    //Max number of clusters to be formed.
    
    private static final double VIGILANCE = 0.5;

    private static final int FONT_WIDTH = 7;

    private static int TRAINING_PATTERNS = 21;    //How many PATTERNS for training weights.
    private static final int IN_PATTERNS = 21;    //Total input patterns.

    private static int pattern[][] = null;

    private static double bw[][] = null;    //Bottom-up weights.
    private static double tw[][] = null;    //Top-down weights.

    private static int f1a[] = null;        //Input layer.
    private static int f1b[] = null;        //Interface layer.
    private static double f2[] = null;

    private static int membership[] = null;
    
    private static void initialize()
    {
        // Insert pattern arrays into pattern[] to make an array of arrays.
        pattern = new int[IN_PATTERNS][N];
        pattern[0] = A1;
        pattern[1] = B1;
        pattern[2] = C1;
        pattern[3] = D1;
        pattern[4] = E1;
        pattern[5] = J1;
        pattern[6] = K1;
        pattern[7] = A2;
        pattern[8] = B2;
        pattern[9] = C2;
        pattern[10] = D2;
        pattern[11] = E2;
        pattern[12] = J2;
        pattern[13] = K2;
        pattern[14] = A3;
        pattern[15] = B3;
        pattern[16] = C3;
        pattern[17] = D3;
        pattern[18] = E3;
        pattern[19] = J3;
        pattern[20] = K3;

        // Initialize bottom-up weight matrix.
        bw = new double[M][N];
        for(int i = 0; i < M; i++)
        {
            for(int j = 0; j < N; j++)
            {
                bw[i][j] = 1.0 / (1.0 + N);
            } // j
        } // i

        // Initialize top-down weight matrix.
        tw = new double[M][N];
        for(int i = 0; i < M; i++)
        {
            for(int j = 0; j < N; j++)
            {
                tw[i][j] = 1.0;
            } // j
        } // i
        
        f1a = new int[N];
        f1b = new int[N];
        f2 = new double[M];
        
        membership = new int[IN_PATTERNS];
        
        return;
    }
    
    private static void ART1()
    {
        int inputSum = 0;
        int activationSum = 0;
        int f2Max = 0;
        boolean reset = true;

        for(int vecNum = 0; vecNum < IN_PATTERNS; vecNum++)
        {
            // Initialize f2 layer activations to 0.0
            for(int i = 0; i < M; i++)
            {
                f2[i] = 0.0;
            }

            // Input pattern() to F1 layer.
            for(int i = 0; i < N; i++)
            {
                f1a[i] = pattern[vecNum][i];
            }

            // Compute sum of input pattern.
            inputSum = vectorSum(f1a);

            // Compute activations for each node in the F1 layer.
            // Send input signal from f1a to the f1b layer.
            for(int i = 0; i < N; i++)
            {
                f1b[i] = f1a[i];
            }

            // Compute net input for each node in the f2 layer.
            for(int i = 0; i < M; i++)
            {
                for(int j = 0; j < N; j++)
                {
                    f2[i] += bw[i][j] * (double)f1a[j];
                } // j
            } // i

            reset = true;
            while(reset == true)
            {
                // Determine the largest value of the f2 nodes.
                f2Max = maximum(f2);

                // Recompute the f1a to f1b activations (perform AND function).
                for(int i = 0; i < N; i++)
                {
                    f1b[i] = f1a[i] * (int)Math.floor(tw[f2Max][i]);
                }

                // Compute sum of input pattern.
                activationSum = vectorSum(f1b);

                reset = testForReset(activationSum, inputSum, f2Max);
            }

            // Only use number of TRAINING_PATTERNS for training, the rest are tests.
            if(vecNum < TRAINING_PATTERNS){
                updateWeights(activationSum, f2Max);
            }

            // Record which cluster the input vector is assigned to.
            membership[vecNum] = f2Max;

        } // vecNum
        return;
    }
    
    private static boolean testForReset(int activationSum, int inputSum, int f2Max)
    {
        if((double)activationSum / (double)inputSum >= VIGILANCE){
            return false;     // Candidate is accepted.
        }else{
            f2[f2Max] = -1.0; // Inhibit.
            return true;      // Candidate is rejected.
        }
    }
    
    private static void updateWeights(int activationSum, int f2Max)
    {
        // Update bw(f2Max)
        for(int i = 0; i < N; i++)
        {
            bw[f2Max][i] = (2.0 * (double)f1b[i]) / (1.0 + (double)activationSum);
        }
        
        // Update tw(f2Max)
        for(int i = 0; i < N; i++)
        {
            tw[f2Max][i] = f1b[i];
        }
        return;
    }
    
    private static int vectorSum(int[] nodeArray)
    {
        int tempSum = 0;

        // Compute sum of input pattern.
        for(int i = 0; i < N; i++)
        {
            tempSum += nodeArray[i];
        }

        return tempSum;
    }
    
    private static int maximum(double[] nodeArray)
    {
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while(!done)
        {
            foundNewWinner = false;
            for(int i = 0; i < M; i++)
            {
                if(i != winner){
                    if(nodeArray[i] > nodeArray[winner]){
                        winner = i;
                        foundNewWinner = true;
                    }
                }
            }

            if(foundNewWinner == false){
                done = true;
            }
        }

        return winner;
    }
    
    private static void printResults()
    {
        int k = 0;

        System.out.println("Input vectors assigned to each cluster:\n");
        for(int i = 0; i < M; i++)
        {
            System.out.print("Cluster # " + i + ": ");
            for(int j = 0; j < IN_PATTERNS; j++)
            {
                if(membership[j] == i){
                    System.out.print(j + ", ");
                }
            } // j
            System.out.print("\n");
        } // i
        
        System.out.println("Final weight values for each cluster:\n");
        for(int i = 0; i < M; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tw[i][j] >= 0.5){
                    System.out.print("#");
                }else{
                    System.out.print(".");
                }

                k += 1;
                if(k == FONT_WIDTH){
                    System.out.print("\n");
                    k = 0;
                }
            } // j
            System.out.print("\n");
        } // i

        return;
    }
    
    
    public static void main(String[] args)
    {
        initialize();
        ART1();
        printResults();
        return;
    }

}