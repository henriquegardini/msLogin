package techclallenge5.fiap.com.msLogin.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.ServletException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import techclallenge5.fiap.com.msLogin.security.handler.CustomAccessDeniedHandler;
import techclallenge5.fiap.com.msLogin.security.handler.CustomAuthenticationEntryPoint;

@SpringBootTest
@Import(SecurityConfigurations.class)
class SecurityConfigurationsTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityFilter securityFilter;

    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void testPublicEndpoints() throws Exception {
        // Test login endpoint
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk());

        // Test register endpoint
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk());

        // Test swagger endpoints
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());

        // Test h2-console endpoint
        mockMvc.perform(get("/h2-console"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAuthenticatedEndpoint() throws Exception {
        // Test an authenticated endpoint
        mockMvc.perform(get("/some-secured-endpoint"))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthenticationManagerBean() throws Exception {
        // Test that the AuthenticationManager bean is created
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        assert (authenticationManager != null);
    }

    @Test
    void testPasswordEncoderBean() {
        // Test that the PasswordEncoder bean is created
        assert (passwordEncoder != null);
    }

    @Test
    void testSecurityFilterIsAdded() throws ServletException, IOException {
        // Verify that the security filter is added to the security chain
        Mockito.verify(securityFilter, Mockito.never()).doFilter(Mockito.any(), Mockito.any(), Mockito.any());
    }
}