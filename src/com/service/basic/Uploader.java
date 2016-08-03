package service.basic;

@FunctionalInterface
public interface Uploader<T> {
	
	void upload(T t);
	
}
