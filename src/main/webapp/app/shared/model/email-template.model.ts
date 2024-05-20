export interface IEmailTemplate {
  id?: number;
  templateId?: number;
  templateCode?: string;
  subject?: string;
  body?: string;
}

export class EmailTemplate implements IEmailTemplate {
  constructor(
    public id?: number,
    public templateId?: number,
    public templateCode?: string,
    public subject?: string,
    public body?: string,
  ) {}
}
