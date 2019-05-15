package ir.asta.wise.core.remoting.rs;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestServerFactory implements ApplicationContextAware,InitializingBean,DisposableBean{
	
	JAXRSServerFactoryBean serverFactory=new JAXRSServerFactoryBean();
	protected ApplicationContext context;
	protected boolean enableLogging = false;
	protected List<Object> providers = new ArrayList<Object>();
	protected List<Feature> features;
	protected String address;
	
	public void setAddress(String address) {
		this.address = address;
	}

	public void setProviders(List<?> p) {
		providers.addAll(p);
	}

	public void setEnableLogging(boolean enableLogging) {
		this.enableLogging = enableLogging;
	}

	public void setFeatures(List<? extends Feature> features2) {
		this.features = CastUtils.cast(features2);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context=applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		serverFactory=new JAXRSServerFactoryBean();

		Map<String, Object> pathToBean=new HashMap<>();
		
		if(context.getParent()!=null)
			findRestBeans(context.getParent(), pathToBean);
		findRestBeans(context, pathToBean);

        serverFactory.setServiceBeans(new ArrayList<>(pathToBean.values()));
        
        if(this.address==null)
        	serverFactory.setAddress("/");	
        else
        	serverFactory.setAddress(address);
        
        if(enableLogging)
        {
        	serverFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        	serverFactory.getInInterceptors().add(new LoggingInInterceptor());
        }	
        serverFactory.setProviders(providers);
        Bus bus=(Bus)context.getBean(Bus.DEFAULT_BUS_ID);
        serverFactory.setBus(bus);
		serverFactory.create();
	}
	
	private void findRestBeans(ApplicationContext context,Map<String,Object> restBeans)
	{	
		if(!(context instanceof AbstractApplicationContext))
			return;
	
		Map<String, Object> annotatedBeans = ((AbstractApplicationContext) context)
				.getBeanFactory().getBeansWithAnnotation(Path.class);
		
		for (Map.Entry<String, Object> entry : annotatedBeans.entrySet()) {    
        	Path path=AnnotationUtils.findAnnotation(entry.getValue().getClass(), Path.class);
        	if(path!=null)
        		restBeans.put(path.value(), entry.getValue());
        }
	}
	
	@Override
	public void destroy() throws Exception {
		 Server server = serverFactory.getServer();
		 if (server != null && server.isStarted()) {
             server.destroy();
         }
	}

}