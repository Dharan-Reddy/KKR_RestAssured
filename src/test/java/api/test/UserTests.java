package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;

	User userPayload;

	@BeforeClass
	public void setUpData() {

		faker = new Faker();

		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUserName(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

	}

	@Test(priority = 1)
	public void testPostUser() {

		Response res = UserEndPoints.createUser(userPayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
	}

	@Test(priority = 2)
	public void testGetUser() {

		Response res = UserEndPoints.readUser(this.userPayload.getUserName());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
	}

	@Test(priority = 3)
	public void testPutUser() {
		
		//Update user details for this method
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response res = UserEndPoints.updateUser(this.userPayload.getUserName(), userPayload);

		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
	}

	@Test(priority=4)
	public void testDeleteUser() {

		Response res = UserEndPoints.deleteUser(this.userPayload.getUserName());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
	}

}
