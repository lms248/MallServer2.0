package main.service.common;

@FunctionalInterface
public interface Uploader<T> {
	
	void upload(T t);
	
}
