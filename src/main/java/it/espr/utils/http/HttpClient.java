package it.espr.utils.http;

public interface HttpClient<Type> {

	public Type get(String url);
	
	public Type get(String url, int timeout, boolean followRedirects, String type);
	
	public void post(String url, Type data);
}
