package games.bevs.library.commons.utils.mojang;


import games.bevs.library.commons.utils.mojang.MojangUtil;

public class Property {
	public String name;
	public String value;
	public boolean signed = true;
	public String signature;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public boolean isSigned() {
		return signed;
	}

	public String getSignature() {
		return signature;
	}

	@Override
	public String toString() {
		return MojangUtil.GSON.toJson(this);
	}

}
