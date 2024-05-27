import static org.junit.Assert.*;

import org.junit.Test;
public class tester {
    @Test
    public void testDenseMazeWithPower() {
        IMazeSolverWithPower mazeSolver = new MazeSolverWithPower();

        try {
            mazeSolver.initialize(Maze.readMaze("maze-dense.txt"));
            int[] comp = new int[16];

            for (int sr = 0; sr <= 3; ++sr) {
                for (int sc = 0; sc <= 3; ++sc) {
                    for (int power = 0; power <= 4; ++power) {
                        for (int i = 0; i < 16; ++i) comp[i] = 0;

                        for (int er = 0; er <= 3; ++er) {
                            for (int ec = 0; ec <= 3; ++ec) {
                                int ans;
                                ans = (int) (Math.abs(sr - er) + Math.abs(sc - ec));
                                if (ans <= power) {
//                                    System.out.println(sr + " " + sc + " " +  er + " " + ec + " " + power);
                                    assertEquals(ans, (int) mazeSolver.pathSearch(sr, sc, er, ec, power));
                                    comp[ans]++;
                                } else {
                                    assertNull(mazeSolver.pathSearch(sr, sc, er, ec, power));
                                }
                            }
                        }

                        for (int i = 0; i < 16; ++i) {
                            assertEquals(comp[i], (int) mazeSolver.numReachable(i));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(true); // no exception should be thrown
        }
    }

    @Test
    public void testEmptyAndDenseMaze() {
        IMazeSolverWithPower mazeSolver = new MazeSolverWithPower();

        try {
            mazeSolver.initialize(Maze.readMaze("maze-empty.txt"));
            int[] comp = new int[16];

            for (int sr = 0; sr <= 3; ++sr) {
                for (int sc = 0; sc <= 3; ++sc) {

                    for (int i = 0; i < 16; ++i) comp[i] = 0;

                    for (int er = 0; er <= 3; ++er) {
                        for (int ec = 0; ec <= 3; ++ec) {
                            // on an empty graph, we calculate Manhattan distance
                            int ans = (int) (Math.abs(sr - er) + Math.abs(sc - ec));
                            assertEquals(ans, (int) mazeSolver.pathSearch(sr, sc, er, ec));

                            comp[ans]++;
                        }
                    }

                    for (int i = 0; i < 16; ++i) {
                        assertEquals(comp[i], (int) mazeSolver.numReachable(i));
                    }
                }
            }

            mazeSolver.initialize(Maze.readMaze("maze-dense.txt"));
            for (int sr = 0; sr <= 3; ++sr) {
                for (int sc = 0; sc <= 3; ++sc) {
                    for (int er = 0; er <= 3; ++er) {
                        for (int ec = 0; ec <= 3; ++ec) {
                            if (sr == er && sc == ec) {
                                assertEquals(0, (int) mazeSolver.pathSearch(sr, sc, er, ec));
                            } else {
                                assertNull(mazeSolver.pathSearch(sr, sc, er, ec));
                            }
                        }
                    }

                    for (int i = 0; i < 16; ++i) {
                        if (i == 0) {
                            assertEquals(1, (int) mazeSolver.numReachable(i));
                        } else {
                            assertEquals(0, (int) mazeSolver.numReachable(i));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(true); // no exception should be thrown
        }
    }
}
