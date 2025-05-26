resource "aws_eip" "nat_eip_1" {
}

resource "aws_eip" "nat_eip_2" {
}

resource "aws_eip" "nat_eip_3" {
}

module "vpc" {
  source             = "./modules/vpc"
  cidr_block         = "10.0.0.0/16"
  public_subnets     = ["10.0.0.0/24", "10.0.3.0/24", "10.0.4.0/24"]
  private_subnets    = ["10.0.1.0/24", "10.0.2.0/24", "10.0.5.0/24"]
  availability_zones = ["us-east-1a", "us-east-1b", "us-east-1c"]
  nat_eip_ids        = [aws_eip.nat_eip_1.id, aws_eip.nat_eip_2.id, aws_eip.nat_eip_3.id]
}

module "load_balancer" {
  source            = "./modules/load_balancer"
  name              = "my-nlb"
  target_group_name = "ecs-tg"
  port              = 80
  vpc_id            = module.vpc.vpc_id
  private_subnets   = module.vpc.private_subnets
}

module "ecs" {
  source             = "./modules/ecs"
  cluster_name       = "my-ecs-cluster"
  task_family        = "my-task"
  execution_role_arn = var.execution_role_arn
  vpc_id             = module.vpc.vpc_id
  service_name       = var.service_name
  desired_count      = var.desired_count
  private_subnets    = module.vpc.private_subnets
  ecs_security_group = module.security.security_group_ecs_id
  elb_security_group = module.security.security_group_elb_id
  target_group_arn   = module.load_balancer.nlb_arn
  container_name     = var.container_name
  container_port     = var.container_port
  container_cpu      = var.container_cpu
  container_memory   = var.container_memory
  container_image    = var.container_image
  aws_region         = var.aws_region
  log_group_name     = var.log_group_name
}

module "api_gateway" {
  source          = "./modules/api_gateway"
  api_name        = "valida-password-gateway"
  api_description = "API para validar passwords"
  integration_uri = module.load_balancer.nlb_dns_name
  nlb_dns_name    = module.ecs.nlb_dns_name
  stage_name      = "dev"
  nlb_arn           = module.load_balancer.nlb_arn
}

module "security" {
  source         = "./modules/security"
  alb_sg_name    = "alb-sg"
  ecs_sg_name    = "ecs-sg"
  vpc_id         = module.vpc.vpc_id
  container_port = var.container_port
}
