package org.scms.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         ---
 *         This should work but doesn't in Embbedded GlassFish. As a turn around I've put the
 * @DataSourceDefinition into the EJB class
 */
public class DatabaseResource {

    @Produces
    @PersistenceContext(unitName = "DataBaseService")
    private EntityManager em;
}