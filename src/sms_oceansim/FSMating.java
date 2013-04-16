    /*
    public static void FSmating()
    {
        char currentFishShark = 'c';
        
        for (int i = 0; i < WORLD.length; i++)
        {
            for (int j = 0; j < WORLD[0].length; j++)
            {
                currentFishShark = WORLD[i][j];
                
                if (currentFishShark == 'F' || currentFishShark == 'S')
                {
                    if (i-1 > 0)
                    {
                        if (WORLD[i-1][j] == currentFishShark)
                        {
                            if (WORLD[i+1][j] == 'O')
                            {
                                WORLD[i+1][j] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i][j-1] == 'O')
                            {
                                WORLD[i][j-1] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i][j+1] == 'O')
                            {
                                WORLD[i][j+1] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }      
                        }
                    }
                    if (i+1 < WORLD.length)
                    {
                        if (WORLD[i+1][j] == currentFishShark)
                        {
                            if (WORLD[i-1][j] == 'O')
                            {
                                WORLD[i-1][j] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i][j-1] == 'O')
                            {
                                WORLD[i][j-1] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i][j+1] == 'O')
                            {
                                WORLD[i][j+1] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                        }
                    }
                    if (j-1 > 0)
                    {
                        if (WORLD[i][j-1] == currentFishShark)
                        {
                            if (WORLD[i+1][j] == 'O')
                            {
                                WORLD[i+1][j] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i][j+1] == 'O')
                            {
                                WORLD[i][j+1] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i-1][j] == 'O')
                            {
                                WORLD[i-1][j] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                        }
                    }
                    if (j+1 < WORLD[0].length)
                    {
                        if (WORLD[i][j+1] == currentFishShark)
                        {
                            if (WORLD[i+1][j] == 'O')
                            {
                                WORLD[i+1][j] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i][j-1] == 'O')
                            {
                                WORLD[i][j-1] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                            else if (WORLD[i-1][j] == 'O')
                            {
                                WORLD[i-1][j] = currentFishShark;
                                MATESTATUS += "Mating \n";
                            }
                        }
                    }
                }
            }
        }
    }
    * */