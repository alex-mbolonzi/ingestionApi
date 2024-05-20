import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetStudy } from 'app/shared/model/dataset-study.model';
import { DatasetStudyService } from './dataset-study.service';

@Component({
  selector: 'jhi-dataset-study-delete-dialog',
  templateUrl: './dataset-study-delete-dialog.component.html',
})
export class DatasetStudyDeleteDialogComponent {
  datasetStudy: IDatasetStudy;

  constructor(
    protected datasetStudyService: DatasetStudyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetStudyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetStudyListModification',
        content: 'Deleted an datasetStudy',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-study-delete-popup',
  template: '',
})
export class DatasetStudyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetStudy }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetStudyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.datasetStudy = datasetStudy;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-study', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-study', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
