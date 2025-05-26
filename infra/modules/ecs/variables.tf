variable "cluster_name" {
  type = string
}

variable "task_family" {
  type = string
}

variable "execution_role_arn" {
  type = string
}

variable "service_name" {
  type = string
}

variable "desired_count" {
  type = number
}

variable "private_subnets" {
  type = list(string)
}

variable "ecs_security_group" {
  type = string
}

variable "elb_security_group" {
  type = string
}

variable "target_group_arn" {
  type = string
}

variable "container_name" {
  type = string
}

variable "container_port" {
  type = number
}

variable "container_cpu" {
  type = number
}

variable "container_memory" {
  type = number
}

variable "container_image" {
  type = string
}

variable "vpc_id" {
  type = string
}

variable "aws_region" {
  type = string
}

variable "log_group_name" {
  type = string
}
