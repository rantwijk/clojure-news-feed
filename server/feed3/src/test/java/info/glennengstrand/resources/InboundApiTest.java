/**
 * News Feed
 * news feed api
 *
 * OpenAPI spec version: 1.0.0
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Eclipse Public License - v 1.0
 *
 * https://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package info.glennengstrand.resources;

import info.glennengstrand.core.InboundApiServiceImpl;
import info.glennengstrand.core.MessageLogger;
import info.glennengstrand.resources.InboundApi.InboundApiService;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * API tests for InboundApi
 */
public class InboundApiTest extends NewsFeedTestBase {

    private InboundApiService api = null;

    @Before
    public void setup() {
    	setupInboundSupport();
    	api = new  InboundApiServiceImpl(inDao, new MessageLogger.DoNothingMessageLogger());
    }
    
    /**
     * retrieve the inbound feed for an individual participant
     *
     * fetch inbound feed by id
     *
     */
    @Test
    public void getInboundTest() {
        assertTrue("fetch did not retrieve the proper content", api.getInbound(TEST_FROM).equals(inFeed));
    }
    
}
