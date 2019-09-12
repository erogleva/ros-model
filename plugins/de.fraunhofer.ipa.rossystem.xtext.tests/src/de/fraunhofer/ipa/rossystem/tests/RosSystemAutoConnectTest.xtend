package de.fraunhofer.ipa.rossystem.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import rossystem.RosSystem
import de.fraunhofer.ipa.ros.sirius.AutoConnect
import org.junit.Test
import org.junit.Assert
import org.eclipse.xtext.util.StringInputStream
import org.eclipse.xtext.resource.XtextResourceSet
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(CustomInjectorProvider))
class RosSystemAutoConnectTest {
	
	@Inject
	ParseHelper<RosSystem> parseHelper
	
	@Inject
	Provider<XtextResourceSet> resourceSetProvider
	
	@Test
	def void testCreatedConnections() {
		
		val resourceSet = resourceSetProvider.get
		val sickS300Example = resourceSet.createResource(URI.createURI("sick_s300.ros"))
		sickS300Example.load(new StringInputStream('''
			PackageSet { package {
				CatkinPackage cob_sick_s300 { artifact {
					Artifact cob_sick_s300 { node Node { name cob_sick_s300 
						publisher { 
						Publisher { name scan message "sensor_msgs.LaserScan" } , 
						Publisher { name diagnostics message "diagnostic_msgs.DiagnosticArray" 
						}}}}
			}}}}
		'''), emptyMap)
		
		val messages = resourceSet.createResource(URI.createURI("msgs.ros"))
		messages.load(new StringInputStream('''PackageSet{package{
		    Package diagnostic_msgs{ spec { 
		      TopicSpec DiagnosticArray{ message { Header header DiagnosticStatus[] status }}  
		    }},
		    Package sensor_msgs{ spec { 
		      TopicSpec LaserScan{ message { Header header float32 angle_min float32 angle_max float32 angle_increment float32 time_increment float32 scan_time float32 range_min float32 range_max float32[] ranges float32[] intensities }}
		    }},
		    Package std_msgs{ spec { 
		    	TopicSpec Bool{ message { bool data }}, 		      
		    }}
		  }
		}'''), emptyMap)
		
		val scanUnifierExample = resourceSet.createResource(URI.createURI("scan_unifier.ros"))
		scanUnifierExample.load(new StringInputStream('''
			PackageSet { package {
				CatkinPackage cob_scan_unifier { artifact {
					Artifact scan_unifier_node { node Node { name scan_unifier_node 
						publisher {
						Publisher { name scan_unified message "sensor_msgs.LaserScan"
						}}
						subscriber {
						Subscriber { name scan3 message "sensor_msgs.LaserScan" } ,
						Subscriber { name scan1 message "sensor_msgs.LaserScan" } ,
						Subscriber { name scan2 message "sensor_msgs.LaserScan" }
						}}}}
			}}}
		'''), emptyMap)
		
		val model = parseHelper.parse('''
		RosSystem { Name 'scan_composition' 
			RosComponents ( 
				ComponentInterface 
					{ name base_laser_front 
						RosPublishers { 
							RosPublisher scan { RefPublisher "cob_sick_s300.cob_sick_s300.cob_sick_s300.scan" } , 
							RosPublisher diagnostics { RefPublisher "cob_sick_s300.cob_sick_s300.cob_sick_s300.diagnostics" }
						}}, 
				ComponentInterface 
					{ name scan_unifier 
						RosPublishers { 
							RosPublisher scan_unified { RefPublisher "cob_scan_unifier.scan_unifier_node.scan_unifier_node.scan_unified" }
						} 
						RosSubscribers { 
							RosSubscriber scan3 { RefSubscriber "cob_scan_unifier.scan_unifier_node.scan_unifier_node.scan3" } , 
							RosSubscriber scan1 { RefSubscriber "cob_scan_unifier.scan_unifier_node.scan_unifier_node.scan1" } , 
							RosSubscriber scan2 { RefSubscriber "cob_scan_unifier.scan_unifier_node.scan_unifier_node.scan2" }
						} } 
					) }
		''', resourceSet)
		
		AutoConnect.autoConnectOpenPorts(model)
		
		// Ensure that topic connections have been created...
		Assert.assertNotNull(model.topicConnections)
		Assert.assertFalse(model.topicConnections.empty)
		
		// ... and are the correct number
		Assert.assertEquals(model.topicConnections.size, 3)
		
		// TODO: test the connections
		// Refactor the resources
		
	}
	
}