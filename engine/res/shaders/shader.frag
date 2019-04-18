#version 330 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[2];
in vec3 toCameraVector;

out vec4  out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor[2];
uniform float shineDamper;
uniform float reflectivity;

void main(void){
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	float nDot1;

	vec3 unitLightVector = normalize(toLightVector[0]);
	nDot1 =  dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.0);
	vec3 resultDiffuse = brightness * lightColor[0];
	
	vec3 lightDir = -unitLightVector;
	vec3 reflectedLightDir = reflect(lightDir, unitNormal);
	
	float specularFactor = dot(reflectedLightDir, unitVectorToCamera); 

	specularFactor = max(specularFactor, 0.0);

	float dampenedFactor = pow(specularFactor, shineDamper);
	vec3 resultSpecular = dampenedFactor * reflectivity * lightColor[0];
	
	vec4 textureColor = texture(textureSampler, pass_textureCoords);
	if(textureColor.a<0.5){
		discard;
	}
	brightness = max(brightness,0.3);
	resultDiffuse = max(resultDiffuse,0.2);
	out_color = vec4(resultDiffuse,1.0) * textureColor + vec4(resultSpecular, 1.0);	
	
	}