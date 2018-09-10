/**
 * generated by Xtext 2.12.0
 */
package de.fraunhofer.ipa.ros.seronetgw.xtext.generator;

import com.google.common.collect.Iterables;
import de.fraunhofer.ipa.ros.seronetgw.rosgw.RosGateway;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import ros.Publisher;
import ros.ServiceClient;
import ros.ServiceServer;
import ros.Subscriber;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class SeronetGwGenerator extends AbstractGenerator {
  private int count_pub;
  
  private int count_sub;
  
  private int count_srvc;
  
  private int count_srvs;
  
  private String ProjectName;
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    this.ProjectName = resource.getURI().toString().substring(19, resource.getURI().toString().lastIndexOf("/"));
    Iterable<RosGateway> _filter = Iterables.<RosGateway>filter(IteratorExtensions.<EObject>toIterable(resource.getAllContents()), RosGateway.class);
    for (final RosGateway node : _filter) {
      fsa.generateFile((("../" + this.ProjectName) + ".rosartifact"), this.compile(node));
    }
  }
  
  public int lenght(final Object x) {
    int _switchResult = (int) 0;
    boolean _matched = false;
    if (x instanceof String) {
      int _length = ((String)x).length();
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        _matched=true;
        _switchResult = ((String)x).length();
      }
    }
    if (!_matched) {
      if (x instanceof List) {
        _matched=true;
        _switchResult = ((List<?>)x).size();
      }
    }
    if (!_matched) {
      _switchResult = (-1);
    }
    return _switchResult;
  }
  
  public CharSequence compile(final RosGateway gateway) {
    CharSequence _xblockexpression = null;
    {
      this.count_pub = this.lenght(gateway.getRosTopicSubscriber());
      this.count_sub = this.lenght(gateway.getRosTopicPublisher());
      this.count_srvc = this.lenght(gateway.getRosServiceServer());
      this.count_srvs = this.lenght(gateway.getRosServiceClient());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Artifact ");
      _builder.append(this.ProjectName);
      _builder.append(" { node Node { name ");
      _builder.append(this.ProjectName);
      _builder.append("_node");
      _builder.newLineIfNotEmpty();
      {
        if ((this.count_srvs > 0)) {
          _builder.append("\t");
          _builder.append("serviceserver {");
          _builder.newLine();
        }
      }
      {
        EList<ServiceClient> _rosServiceClient = gateway.getRosServiceClient();
        for(final ServiceClient srvs : _rosServiceClient) {
          final int count_srvs = this.count_srvs--;
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("ServiceServer { name \"");
          String _name = srvs.getName();
          _builder.append(_name, "\t\t");
          _builder.append("\" service \"");
          String _name_1 = srvs.getService().getPackage().getName();
          _builder.append(_name_1, "\t\t");
          _builder.append(".");
          String _name_2 = srvs.getService().getName();
          _builder.append(_name_2, "\t\t");
          _builder.append("\" } ");
          {
            if ((count_srvs > 1)) {
              _builder.append(",");
            } else {
              _builder.append("}");
            }
          }
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((this.count_pub > 0)) {
          _builder.append("\t");
          _builder.append("publisher {");
          _builder.newLine();
        }
      }
      {
        EList<Subscriber> _rosTopicSubscriber = gateway.getRosTopicSubscriber();
        for(final Subscriber pub : _rosTopicSubscriber) {
          final int count_pub = this.count_pub--;
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("Publisher { name \"");
          String _name_3 = pub.getName();
          _builder.append(_name_3, "\t\t");
          _builder.append("\" message \"");
          String _name_4 = pub.getMessage().getPackage().getName();
          _builder.append(_name_4, "\t\t");
          _builder.append(".");
          String _name_5 = pub.getMessage().getName();
          _builder.append(_name_5, "\t\t");
          _builder.append("\" } ");
          {
            if ((count_pub > 1)) {
              _builder.append(",");
            } else {
              _builder.append("}");
            }
          }
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((this.count_sub > 0)) {
          _builder.append("\t");
          _builder.append("subscriber {");
          _builder.newLine();
        }
      }
      {
        EList<Publisher> _rosTopicPublisher = gateway.getRosTopicPublisher();
        for(final Publisher sub : _rosTopicPublisher) {
          final int count_sub = this.count_sub--;
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("Subscriber { name \"");
          String _name_6 = sub.getName();
          _builder.append(_name_6, "\t\t");
          _builder.append("\" message \"");
          String _name_7 = sub.getMessage().getPackage().getName();
          _builder.append(_name_7, "\t\t");
          _builder.append(".");
          String _name_8 = sub.getMessage().getName();
          _builder.append(_name_8, "\t\t");
          _builder.append("\" } ");
          {
            if ((count_sub > 1)) {
              _builder.append(",");
            } else {
              _builder.append("}");
            }
          }
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((this.count_srvc > 0)) {
          _builder.append("\t");
          _builder.append("serviceclient {");
          _builder.newLine();
        }
      }
      {
        EList<ServiceServer> _rosServiceServer = gateway.getRosServiceServer();
        for(final ServiceServer srvc : _rosServiceServer) {
          final int count_srvc = this.count_srvc--;
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("\t");
          _builder.append("ServiceClient { name \"");
          String _name_9 = srvc.getName();
          _builder.append(_name_9, "\t\t");
          _builder.append("\" service \"");
          String _name_10 = srvc.getService().getPackage().getName();
          _builder.append(_name_10, "\t\t");
          _builder.append(".");
          String _name_11 = srvc.getService().getName();
          _builder.append(_name_11, "\t\t");
          _builder.append("\" } ");
          {
            if ((count_srvc > 1)) {
              _builder.append(",");
            } else {
              _builder.append("}");
            }
          }
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}}");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
}
