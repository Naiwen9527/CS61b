import java.lang.reflect.Array;

public class NBody {
    public static double readRadius(String file_name){
        In universe=new In(file_name);
        int numbers= universe.readInt();
        double radius=universe.readDouble();
        return radius;
    }

    public static Body[] readBodies(String file_name){
        In universe=new In(file_name);
        int numbers=universe.readInt();
        double radius=universe.readDouble();
        Body[] turd=new Body[numbers];
        int p=0;
        while (p < numbers) {
            turd[p]=new Body(universe.readDouble(), universe.readDouble(), universe.readDouble(), universe.readDouble(),universe.readDouble(), universe.readString());
            p+=1;
        }
        
        return turd;
    }

        public static void main(String[] args){
            double T=Double.parseDouble(args[0]);
            double dt=Double.parseDouble(args[1]);
            String filename=args[2];

            Body[] bodies=readBodies(filename);
            double r=readRadius(filename);

            StdDraw.setScale(-r, r);

            
            StdDraw.enableDoubleBuffering();

            for (double i = 0; i <= T; i = i + dt){
                   StdDraw.clear();
                   StdDraw.picture(0,0,"images/starfield.jpg");
                double[] xForces= new double[bodies.length];
                double[] yForces= new double[bodies.length];
                for (int n=0; n<bodies.length; n+=1){
                    xForces[n]=bodies[n].calcNetForceExertedByX(bodies);
                    yForces[n]=bodies[n].calcNetForceExertedByY(bodies);
                }
                for (int j=0; j<bodies.length; j+=1){
                    bodies[j].update(dt, xForces[j], yForces[j]);
                    bodies[j].draw();
                }
            
        StdDraw.show();
		StdDraw.pause(10);
            }
            StdOut.printf("%d\n", bodies.length);
StdOut.printf("%.2e\n", r);
for (int i = 0; i < bodies.length; i++) {
    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
}
        
        }
}
