package ungphys.lund;

public class LUNDParticle {
	
	public int index, pid, type; 
	public double px, py, pz, vx, vy, vz, lifeTime, E, mass;
	
	public LUNDParticle (String particleRow) {
		String[] parts = particleRow.split("\\s+");
		pid = Integer.parseInt(parts[4]);
		index = Integer.parseInt(parts[1]);
		lifeTime = Double.parseDouble(parts[2]);
		type = Integer.parseInt(parts[3]);
		px = Double.parseDouble(parts[7]);
		py = Double.parseDouble(parts[8]);
		pz = Double.parseDouble(parts[9]);
		E = Double.parseDouble(parts[10]);
		mass = Double.parseDouble(parts[11]);
		vx = Double.parseDouble(parts[12]);
		vy = Double.parseDouble(parts[13]);
		vz = Double.parseDouble(parts[14]);
	}

	public int getIndex() {
		return index;
	}

	public double getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(double lifeTime) {
		this.lifeTime = lifeTime;
	}

	public double getE() {
		return E;
	}

	public void setE(double e) {
		E = e;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getPx() {
		return px;
	}

	public void setPx(double px) {
		this.px = px;
	}

	public double getPy() {
		return py;
	}

	public void setPy(double py) {
		this.py = py;
	}

	public double getPz() {
		return pz;
	}

	public void setPz(double pz) {
		this.pz = pz;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getVz() {
		return vz;
	}

	public void setVz(double vz) {
		this.vz = vz;
	}
	

}
