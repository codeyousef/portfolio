export interface Service {
  id: string;
  title: string;
  shortDescription: string;
  fullDescription: string;
  iconClass: string;
  price: number | null;
  features: string[];
  ctaText: string;
  ctaLink: string;
  displayOrder: number;
  featured: boolean;
  createdAt: string;
  updatedAt: string;
  detailsLink: string;
} 