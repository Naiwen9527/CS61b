public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public Body(double xP, double yP, double xV,
              double yV, double m, String img){
                  xxPos=xP;
                  yyPos=yP;
                  xxVel=xV;
                  yyVel=yV;
                  mass=m;
                  imgFileName=img;
              }
    public Body(Body b){
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public double calcDistance(Body b){
        double squared=(Math.pow(b.xxPos-this.xxPos, 2.0)+Math.pow(b.yyPos-this.yyPos, 2.0));
        return Math.pow(squared, 0.5);
    }

    public double calcForceExertedBy(Body b){
        if (this.equals(b)){
            return 0;
        }
        else {
            return (6.67*(Math.pow(10, -11))*this.mass*b.mass)/Math.pow(calcDistance(b), 2);
        }
    }

    public double calcForceExertedByX(Body b){
        return calcForceExertedBy(b)*(b.xxPos-this.xxPos)/calcDistance(b);
    }

    public double calcForceExertedByY(Body b){
        return calcForceExertedBy(b)*(b.yyPos-this.yyPos)/calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] lst){
        double sum=0;
        for (Body b : lst){
            if (this.equals(b)){continue;}
            sum+=calcForceExertedByX(b);
        }
        return sum;
    }

    public double calcNetForceExertedByY(Body[] lst){
        double sum=0;
        for (Body b : lst){
            if (this.equals(b)){continue;}
            sum+=calcForceExertedByY(b);
        }
        return sum;
    }

    public void update(double t, double fx, double fy){
        double newVx=xxVel+(t*(fx/mass));
        double newVy=yyVel+(t*(fy/mass));
        double newPx=xxPos+(newVx*t);
        double newPy=yyPos+(newVy*t);
        xxVel=newVx;
        yyVel=newVy;
        xxPos=newPx;
        yyPos=newPy;
    }

    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
    }
}