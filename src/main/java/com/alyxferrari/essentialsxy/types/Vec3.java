package com.alyxferrari.essentialsxy.types;
public class Vec3 {
	public double x;
	public double y;
	public double z;
	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	@Override
	public boolean equals(Object object) {
		if (object instanceof Vec3) {
			Vec3 temp = (Vec3) object;
			if (x == temp.x && y == temp.y && z == temp.z) {
				return true;
			}
		}
		return false;
	}
}