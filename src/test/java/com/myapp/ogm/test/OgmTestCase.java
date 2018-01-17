package com.myapp.ogm.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import com.myapp.ogm.test.domain.User;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


public class OgmTestCase {

    private Session session;
    private java.util.logging.Logger l;

    @Before
    public void setUp() throws Exception {

        Configuration configuration = new Configuration.Builder()
            .uri("http://neo4j:password@localhost:7474")
            //.uri(neoServer.boltURI().toString())
            //.uri(neoServer.httpURI().toString()) // For HTTP
            //.uri(new File("target/graph.db").toURI().toString()) // For Embedded
            .build();

        SessionFactory sessionFactory = new SessionFactory(configuration, User.class.getPackage().getName());
        session = sessionFactory.openSession();
        session.purgeDatabase();
    }

    @Test
    public void sampleTest() {
        User user = new User("noone@nowhere.com", "No", "One");
        session.save(user);

        Collection<User> allUsers = session.loadAll(User.class);
        assertThat(allUsers).hasSize(1);

        assertThat(allUsers.iterator().next().getEmail()).isEqualTo("noone@nowhere.com");
    }
}
