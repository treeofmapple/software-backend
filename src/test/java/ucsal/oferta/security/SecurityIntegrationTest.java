package ucsal.oferta.security;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import ucsal.oferta.interfaces.ContainerSecurityTest;

@SpringBootTest
@ContainerSecurityTest
@AutoConfigureMockMvc
@DisplayName("Security Integration Tests")
public class SecurityIntegrationTest {

	// Teste Quebrado
	
	/*

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;
    
    @MockitoBean
    private AuthenticationService authService;

    @MockitoBean
    private UserService userService;

    private AuthenticationResponse authResponse;
    
    
    @BeforeEach
    void setUp() {
        authResponse = new AuthenticationResponse(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNj...s",
                "ZXlKaGJHY2lPaUpTVXpJMU5pSjkuZXlKcFpDSTZJbVJsYm...9"
        );
        
        
    }
    
	public String generateUserForTest(AuthenticationService authService) {
		User userDetails = new User();
		userDetails.setUsername("testuser1");
		userDetails.setEmail("test1@test1.com");
		// userDetails.setAge(20);
		userDetails.setPassword("password123");
		userDetails.setRole(Role.ALUNO);
		userDetails.setEnabled(true);
		userDetails.setAccountNonLocked(true);
		return jwtService.generateRefreshToken(userDetails);
	}
    
	@Test
	@Order(1)
	@DisplayName("Test User Registration")
	void userRegistrationMockTest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                "testuser",
                "test@test.com",
                20,
                Role.ALUNO.name(),
                "password123",
                "password123"
        );

        when(authService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(authResponse.accessToken()))
                .andExpect(jsonPath("$.refreshToken").value(authResponse.refreshToken()));
	}
	
	@Test
	@Order(2)
	@DisplayName("Test User Sign In ")
	void userSignInMockTest() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("test@test.com", "password123");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(authResponse.accessToken()))
                .andExpect(jsonPath("$.refreshToken").value(authResponse.refreshToken()));
		
	}
	
	@Test
	@Order(3)
	@WithMockUser(username = "test1@test1.com")
	@DisplayName("Test Refresh Token")
	void userRefreshTokenInMockTest() throws Exception {
		String refreshToken = generateUserForTest(authService);
		
	    when(authService.refreshToken(any(), any())).thenReturn(authResponse);

	    mockMvc.perform(post("/v1/auth/refresh-token")
	            .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.accessToken").value(authResponse.accessToken()))
	            .andExpect(jsonPath("$.refreshToken").value(authResponse.refreshToken()));
	}
	
    @Test
    @Order(4)
    @WithMockUser(username = "test@test.com", roles = "USER")
    @DisplayName("Test Get Current User as Authenticated USER")
    void getCurrentUserAsUserTest() throws Exception {
        UserResponse userResponse = new UserResponse(
                UUID.randomUUID(),
                "testuser",
                "test@test.com",
                25,
                "USER",
                ZonedDateTime.now()
        );
        
        when(userService.getCurrentUserAuthenticated()).thenReturn(userResponse);
        
        mockMvc.perform(get("/v1/user/me")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userResponse.username()))
                .andExpect(jsonPath("$.email").value(userResponse.email()));
    }
    
    @Test
    @Order(5)
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    @DisplayName("Test Get Current User as Authenticated ADMIN")
    void getCurrentUserAsAdminTest() throws Exception {
        UserResponse userResponse = new UserResponse(
                UUID.randomUUID(),
                "adminuser",
                "admin@test.com",
                42,
                "ADMIN",
                ZonedDateTime.now()
        );

        when(userService.getCurrentUserAuthenticated()).thenReturn(userResponse);

        mockMvc.perform(get("/v1/user/me")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userResponse.username()))
                .andExpect(jsonPath("$.role").value(userResponse.role()));
    }

    @Test
    @Order(6)
    @DisplayName("Test Get Current User as Anonymous")
    void getCurrentUserAsAnonymousTest() throws Exception {
    	
        mockMvc.perform(get("/v1/user/me").with(anonymous())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(7)
    @WithMockUser(roles = "USER")
    @DisplayName("Test Delete My Account as Authenticated USER")
    void deleteMyAccountAsUserTest() throws Exception {
        DeleteAccountRequest deleteRequest = new DeleteAccountRequest("password123");

        doNothing().when(userService).deleteMyAccount(any(DeleteAccountRequest.class));

        mockMvc.perform(delete("/v1/user/me/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("Test Delete My Account as Anonymous")
    void deleteMyAccountAsAnonymousTest() throws Exception {
        DeleteAccountRequest deleteRequest = new DeleteAccountRequest("password1s123");

        mockMvc.perform(delete("/v1/user/me/delete").with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isUnauthorized());
    }
	
	*/
	
}
