import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequestStatusDetails } from 'app/shared/model/request-status-details.model';
import { RequestStatusDetailsService } from './request-status-details.service';
import { RequestStatusDetailsComponent } from './request-status-details.component';
import { RequestStatusDetailsDetailComponent } from './request-status-details-detail.component';
import { RequestStatusDetailsUpdateComponent } from './request-status-details-update.component';
import { RequestStatusDetailsDeletePopupComponent } from './request-status-details-delete-dialog.component';
import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';

@Injectable({ providedIn: 'root' })
export class RequestStatusDetailsResolve implements Resolve<IRequestStatusDetails> {
  constructor(private service: RequestStatusDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequestStatusDetails> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RequestStatusDetails>) => response.ok),
        map((requestStatusDetails: HttpResponse<RequestStatusDetails>) => requestStatusDetails.body),
      );
    }
    return of(new RequestStatusDetails());
  }
}

export const requestStatusDetailsRoute: Routes = [
  {
    path: '',
    component: RequestStatusDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.requestStatusDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestStatusDetailsDetailComponent,
    resolve: {
      requestStatusDetails: RequestStatusDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.requestStatusDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestStatusDetailsUpdateComponent,
    resolve: {
      requestStatusDetails: RequestStatusDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.requestStatusDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestStatusDetailsUpdateComponent,
    resolve: {
      requestStatusDetails: RequestStatusDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.requestStatusDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const requestStatusDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RequestStatusDetailsDeletePopupComponent,
    resolve: {
      requestStatusDetails: RequestStatusDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.requestStatusDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
