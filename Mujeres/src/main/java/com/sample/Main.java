package com.sample;

import java.io.FileInputStream;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class Main {
	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KieServices kieServices = KieServices.Factory.get();
			KieFileSystem kfs = kieServices.newKieFileSystem();

			// for each DRL file, referenced by a plain old path name:
			FileInputStream fis = new FileInputStream( "src/main/resources/rules/ExamenPrimerParcial.drl" );
			kfs.write( "src/main/resources/simple.drl",
					kieServices.getResources().newInputStreamResource( fis ) );
			KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
			Results results = kieBuilder.getResults();
			if( results.hasMessages( Message.Level.ERROR ) ){
				System.out.println( results.getMessages() );
				throw new IllegalStateException( "### errors ###" );
			}

			KieContainer kieContainer =
					kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );

			KieBaseConfiguration kieBaseConf=kieServices.newKieBaseConfiguration();
			kieBaseConf.setOption(org.kie.api.conf.EqualityBehaviorOption.EQUALITY);
			KieBase kb = kieContainer.newKieBase(kieBaseConf);
			KieSession kieSession = kb.newKieSession();
			kieSession.fireAllRules();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}

