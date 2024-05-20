import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';

export interface IStatus {
  id?: number;
  statusId?: number;
  statusCode?: string;
  statusName?: string;
  requestStatusDetails?: IRequestStatusDetails[];
}

export class Status implements IStatus {
  constructor(
    public id?: number,
    public statusId?: number,
    public statusCode?: string,
    public statusName?: string,
    public requestStatusDetails?: IRequestStatusDetails[],
  ) {}
}
