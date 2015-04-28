/*
 * Copyright 2014 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.cpr;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceImpl;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

/**
 * Hack to avoid any web.xml file: to duplicate this class allows to add the annotation WebListener
 */
@WebListener
public class SessionSupport implements HttpSessionListener {

    private final Logger logger = LoggerFactory.getLogger(SessionSupport.class);

    // Quite ugly, but gives hints about current state of Session Support.
    public static boolean initializationHint = false;

    public SessionSupport() {
        initializationHint = true;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.debug("Session created");
    }

    @SuppressWarnings("deprecation")
	@Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.debug("Session destroyed");
        try {
            HttpSession s = se.getSession();
            if (BroadcasterFactory.getDefault() != null) {
                for (Broadcaster b : BroadcasterFactory.getDefault().lookupAll()) {
                    for (AtmosphereResource r : b.getAtmosphereResources()) {
                        if (r.session() != null && r.session().getId().equals(s.getId())) {
                            AtmosphereResourceImpl.class.cast(r).session(null);
                        }
                    }
                } 
            }
        } catch (Throwable t) {
            logger.warn("", t);
        }
    }
}
