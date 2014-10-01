package org.nirbo.Persistence;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import org.nirbo.Model.SplashMessages;

import javax.persistence.EntityManager;

public class CinemallJPAContainer extends JPAContainer<SplashMessages> {

    private static final String PERSISTENCE_UNIT = "cinemallPU";

    public CinemallJPAContainer() {
        super(SplashMessages.class);
        EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit(PERSISTENCE_UNIT);
        setEntityProvider(new CachingMutableLocalEntityProvider<SplashMessages>(SplashMessages.class, em));
    }
}
