package testclasses;

import base.TestBase;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pages.Comic;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(DataProviderRunner.class)
public class MarvelComicsTest extends TestBase {
    Comic comicPage;
    private static RequestSpecification requestSpec;
    private static String MARVEL_BASE_URI = "https://gateway.marvel.com";

    public MarvelComicsTest() {
        super();
    }

    @Before
    public void setUp() {
        String privateKey = getPrivateKey();
        String publicKey = getPublicKey();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String ts = String.valueOf(timestamp.getTime());
        String md5Hash = DigestUtils.md5Hex(ts + privateKey + publicKey);

        requestSpec = new RequestSpecBuilder().
                setUrlEncodingEnabled(false).
                setBaseUri(MARVEL_BASE_URI).
                addQueryParam("ts", ts).
                addQueryParam("apikey", publicKey).
                addQueryParam("hash", md5Hash.toLowerCase()).
                build();
    }

    @DataProvider
    public static Object[] characterIds() throws IOException {
        List<String> characterIds = readFromFile("./src/test/resources/characterIdsTestData.txt");
        Object[] data = new String[characterIds.size()];
        for (int i = 0; i < characterIds.size(); i++) {
            data[i] = characterIds.get(i);
        }
        return data;
    }

    @Test
    @UseDataProvider("characterIds")
    public void verifyHomePageTitleTest(int characterId)  {

        //call marvel api to get comics returned
        Response response = given().
                spec(requestSpec).
                and().
                pathParam("characterId", characterId).
                get("/v1/public/characters/{characterId}").
                then().
                assertThat().statusCode(200).
                extract().response();

        int comicsFromApiResponse = response.path("data.results[0].comics.available");
        String url = response.path("data.results[0].urls.find{it.type=='comiclink'}.url");

        //get comics from webpage
        comicPage = initialization(url);

        //get the list of comics
        int comicListSizeOnWeb = comicPage.getComicListSizeOnWeb();

        //compare webList vs api_List
        Assert.assertEquals(comicsFromApiResponse, comicListSizeOnWeb);
    }

    @After
    public void tearDown()  {
        if (driver != null)
            driver.quit();
    }
}
