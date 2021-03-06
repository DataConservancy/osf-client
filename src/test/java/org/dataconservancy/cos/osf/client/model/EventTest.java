/*
 * Copyright 2016 Johns Hopkins University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dataconservancy.cos.osf.client.model;

import com.github.jasminb.jsonapi.ResourceList;
import org.dataconservancy.cos.osf.client.service.OsfService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests mapping between the JSON of a 'logs' document and the Java model for the Event class
 */
public class EventTest extends AbstractMockServerTest {

    @Rule
    public TestName testName = new TestName();

    private OsfService osfService;

    @Before
    public void setUp() throws Exception {
        factory.interceptors().add(new RecursiveInterceptor(testName, EventTest.class, getBaseUri()));
        osfService = factory.getOsfService(OsfService.class);
    }

    @Test
    public void testEventMapping() throws Exception {
        String registrationId = "eq7a4";

        Registration registration = osfService.registration(registrationId).execute().body();
        assertNotNull(registration);
        assertNotNull(registration.getLogs());

        ResourceList<Event> events = osfService.getLogs(registration.getLogs()).execute().body();

        String eventId = "57570a06c7950c0045ac803e";

        Event event = events.stream()
                .filter(e -> e.getId().equals(eventId))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Expected event stream to contain event id " + eventId));

        // additional assertions

        String expectedAction = "node_removed";
        String expectedDate = "2016-06-07T17:52:19.617000";
        String expectedNode = "eq7a4/";
        String expectedOrigNode = "5w8q7/";
        String expectedUser = "qmdz6/";

        assertEquals(expectedAction, event.getAction());
        assertEquals(expectedDate, event.getDate());
        assertTrue(event.getNode().endsWith(expectedNode));
        assertTrue(event.getOriginal_node().endsWith(expectedOrigNode));
        assertTrue(event.getUser().endsWith(expectedUser));

        assertTrue(event.getParams().containsKey("params_node"));
        assertTrue(event.getParams().containsKey("params_project"));
        assertTrue(((Map) event.getParams().get("params_node")).get("id").equals("5w8q7"));
        assertTrue(((Map) event.getParams().get("params_node")).get("title").equals("Workflow Execution"));
    }
}
