output "api_gateway_url" {
  value = aws_api_gateway_deployment.validate_deployment.invoke_url
}

output "vpc_link_id" {
  value = aws_api_gateway_vpc_link.ecs_vpc_link.id
}