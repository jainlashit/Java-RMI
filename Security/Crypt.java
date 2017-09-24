public class Crypt{
	
	public Crypt(){}

	public byte[] encrypt(byte[] data, byte[] key)
	{
		int j = 0;
		for (int i = 0; i < data.length; i++) {

			if(j == key.length)
				j = 0;
			
			data[i] = (byte) (data[i] ^ key[j]);
			
		}
		return data;
	}

	public byte[] decrypt(byte[] data, byte[] key)
	{
		int j = 0;
		for (int i = 0; i < data.length; i++) {

			if(j == key.length)
				j = 0;
			
			data[i] = (byte) (data[i] ^ key[j]);
			
		}
		return data;
	}
}