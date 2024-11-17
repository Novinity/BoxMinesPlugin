package io.github.novinity.boxmines.utils;

public class Vector3 {
    public double X,Y,Z;

    public Vector3(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }

    @Override
    public String toString() {
        return X + ", " + Y + ", " + Z;
    }
}
